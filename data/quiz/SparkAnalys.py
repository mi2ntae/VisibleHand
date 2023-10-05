
import pandas as pd
import numpy as np
import os
import sys
import math
import re
from datetime import datetime
from pytz import timezone
from konlpy.tag import Komoran
# 시간 측정
import time

import pymysql
from pyspark import SparkContext
from pyspark.sql import SparkSession
from konlpy.tag import Kkma 
pymysql.install_as_MySQLdb()
from konlpy.tag import Kkma
import sqlalchemy
from pyspark.sql.types import StringType, DoubleType
from pyspark.sql.window import Window

from pyspark.ml.feature import HashingTF, IDF, Tokenizer
from pyspark.ml.feature import Normalizer

from datetime import date
from datetime import datetime
from bs4 import BeautifulSoup

def setStopWords(path):
  stopword = set()
  
  # 구분자 설정
  
  f = open(path, 'r', encoding='UTF-8')
  lines = f.readlines()
  
  for line in lines:
    stopword.add(line.replace('\n', '$'))
  # print("#### 불용어 설정 완료 ####")
  stopword = '|'.join(stopword)
  return stopword

def getTFIDF(df):
  # TF-IDF
  print("### TF-IDF 계산 ###")
  
  if df.count() == 0:
    print("데이터가 존재하지 않습니다.")
    return
  
  start = time.time() # 시작 시간
  
  tokenizer = Tokenizer(inputCol='keywords', outputCol='test')
  wordsData = tokenizer.transform(df)
  
  hashingTF = HashingTF(inputCol="test", outputCol="tf")
  tf = hashingTF.transform(wordsData)
  
  idf = IDF(inputCol="tf", outputCol="feature").fit(tf)
  tfidf = idf.transform(tf)
  # L2 norm
  
  normalizer = Normalizer(inputCol="feature", outputCol="norm")
  data = normalizer.transform(tfidf)
  print(f">> TF-IDF 소요 시간 : {time.time() - start:.5f} 초")

  return data


print("----------Spark Big Data Pipe Line Start---------------")


os.environ['PYSPARK_PYTHON'] = sys.executable
os.environ['PYSPARK_DRIVER_PYTHON'] = sys.executable

db_connection_str = 'mysql+pymysql://root:melon1*@j9a504.p.ssafy.io/it'
db_connection = sqlalchemy.create_engine(db_connection_str)
conn = db_connection.connect()

print("----------DataBase Connection Suc---------------")

Kkma = Kkma()


sql = "select article_id,content,kind from article where issue_date>=date(now() - INTERVAL 1 DAY) order by article_id asc;"
pd_df = pd.read_sql_query(sql, conn)

print("----------Query Exec Suc---------------")


stopwords = setStopWords('stopword.txt')
print(stopwords)
pd_df = pd_df.dropna(axis='columns', how='all')
pd_df['content'] = [BeautifulSoup(a, 'html.parser').text for a in pd_df['content']]
sc = SparkContext.getOrCreate()
spark = SparkSession.builder\
                    .master('local[*]')\
                    .appName('spark')\
                    .config("spark.network.timeout","1200000s")\
                    .config("spark.network.timeout", "600000s")\
                    .config("spark.executor.heartbeatInterval","3600s")\
                    .config("spark.executor.memory", "5g")\
                    .getOrCreate()
spark.conf.set("spark.sql.execution.arrow.pyspark.enabled", "false")


print("----------Spark Session Build Suc--------------")

df_data = [ a for a in pd_df.itertuples()]
df_column = [a for a in pd_df.columns]
df_column = ["Index"] + df_column


news_df = spark.createDataFrame(data=df_data,schema=df_column)


df = (news_df.rdd.map(lambda x: (x.article_id,x.kind,x.content)).toDF()
      .withColumnRenamed("_1","news_id")
      .withColumnRenamed("_2","kind")
      .withColumnRenamed("_3","content")).toPandas()
      
# 불용어 제거 
content_list  = df[['news_id','content','kind']].index.tolist()
clean_words = [] 
# for idx, article in enumerate(content_list):
for idx in content_list:
    # try:
    
    content = df.loc[idx]["content"].replace("\n","")
    # if(content == None):
        # print("12312312312")
    # print("______________________")
    # print(content.replace("\n",""))
    # 형태소 분석기 초기화
    komoran = Komoran()

# 텍스트를 형태소 단위로 분할하고 명사만 추출
    nouns = komoran.nouns(content)


    for word in nouns:
        # if(word==" "):
        #     continue
        if(len(word)>1):
            clean_words.append((df.loc[idx]["news_id"], word))
    # print(word)


print("------------불용어 처리 Suc-----------------")


#TF-IDF Spark

lines=sc.parallelize(clean_words)
map1=lines.flatMap(lambda x: [((x[0],x[1]),1)])

reduce=map1.reduceByKey(lambda x,y:x+y)
tf=reduce.map(lambda x: (x[0][1],(x[0][0],x[1])))

map3=reduce.map(lambda x: (x[0][1],(x[0][0],x[1],1)))
map4=map3.map(lambda x:(x[0],x[1][2]))
reduce2=map4.reduceByKey(lambda x,y:x+y)
idf=reduce2.map(lambda x: (x[0],math.log2(len(clean_words)/x[1])))
rdd=tf.join(idf)

rdd=rdd.map(lambda x: (x[1][0][0],(x[0],x[1][0][1],x[1][1],x[1][0][1]*x[1][1]))).sortByKey()
rdd=rdd.map(lambda x: (x[0],x[1][0],x[1][1],x[1][2],x[1][3]))


result = rdd.collect()

result_pd =pd.DataFrame(result, columns=["article_id","word","TF","IDF","TF-IDF"])


print("------------TF-IDF 처리 완료-----------------")
result_pd = result_pd.drop(columns=['TF','IDF'])
merge_df = pd.merge(result_pd,pd_df[['article_id','kind']],how="inner",on = "article_id")
# 여기서 기사별로 저장되고
# 그뒤에 kind를 붙인다음에 rdd를 써야겠구나



top_words_df = merge_df.groupby('article_id', group_keys=False).apply(lambda group: group.nlargest(25, 'TF-IDF')).reset_index(drop=True)

top_words_df =top_words_df.rename(columns  = {'TF-IDF': 'score'})
print(top_words_df)
temp = top_words_df[['article_id','score','word']]
temp.to_sql(name='cloud_article', con=db_connection, if_exists='append',index=False)  
conn.commit()

# 결과 출력
print("------------TF-IDF 저장 완료-----------------")
###### word count 시작
# 
df_data = [ a for a in top_words_df.itertuples()]
df_column = [a for a in top_words_df.columns]
df_column = ["Index"] + df_column


news_df = spark.createDataFrame(data=df_data,schema=df_column)
df = (news_df.rdd.map(lambda x: (x.word,x.kind)).toDF()
      .withColumnRenamed("_1","word")
      .withColumnRenamed("_2","kind")).toPandas()


word_list  = df[['word','kind']].index.tolist()
clean_words = [] 

for idx in word_list:
      clean_words.append((df.loc[idx]["word"], df.loc[idx]["kind"]))
#TF-IDF Spark

lines=sc.parallelize(clean_words)
map1=lines.flatMap(lambda x: [((x[0],x[1]),1)])

reduce=map1.reduceByKey(lambda x,y:x+y)
print(reduce.collect())

result = reduce.map(lambda x: (x[0][0],x[0][1],x[1]))
result = result.collect()
result_pd =pd.DataFrame(result, columns=["word","kind","count"])
# spark.stop()


result_pd.insert(3,'issue_date',datetime.today().strftime("%Y-%m-%d"),allow_duplicates=True)

print("------------Word Count 분석 및 저장 완료 -----------------")
result_pd = result_pd[['word','count','issue_date','kind']]
result_pd = result_pd.groupby('kind', group_keys=False).apply(lambda group: group.nlargest(25, 'count')).reset_index(drop=True)

result_pd.to_sql(name='cloud', con=db_connection, if_exists='append',index=False)  

spark.stop()

print("SparkAnalys Python Script Succ")