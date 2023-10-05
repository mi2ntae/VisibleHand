import torch
from transformers import PreTrainedTokenizerFast
from transformers import BartForConditionalGeneration
from tqdm import tqdm
import sqlalchemy
import os

import re
from bs4 import BeautifulSoup
import pandas as pd

db_connection_str = 'mysql+pymysql://root:melon1*@j9a504.p.ssafy.io/it'
db_connection = sqlalchemy.create_engine(db_connection_str)
conn = db_connection.connect()
sql = '''SELECT
    a.article_id,
    a.content,
    ca.word
FROM
    article a
INNER JOIN (
    SELECT
        article_id,
        MAX(score) AS max_score
    FROM
        cloud_article
    GROUP BY
        article_id
) max_scores
ON
    a.article_id = max_scores.article_id
INNER JOIN
    cloud_article ca
ON
    a.article_id = ca.article_id AND ca.score = max_scores.max_score
    where a.issue_date>=date(now() - INTERVAL  1 DAY);'''
pd_df = pd.read_sql_query(sql, conn)
pd_df['content'] = [BeautifulSoup(a, 'html.parser').text for a in pd_df['content']]
print(pd_df)

tokenizer = PreTrainedTokenizerFast.from_pretrained('Sehong/kobart-QuestionGeneration')
model = BartForConditionalGeneration.from_pretrained('Sehong/kobart-QuestionGeneration')

text_list =[]
pd_df_list = pd_df.index.to_list()
for idx in tqdm(pd_df_list):
    text = pd_df['content'][idx]
    soup = BeautifulSoup(text, 'html.parser')
    text = soup.text.replace("\n","").replace("\t","").replace('\\','')
    text  = re.sub(r"[^\uAC00-\uD7A3\s]", "", text)
    while text.find("  ") != -1 :
        text = text.replace("  "," ")
    text_list.append((text if len(text) < 1800 else text[:1800])  + "<unused0>"+ pd_df['word'][idx].replace(" ",""))
quiz_list = []
print("----------init suc-------------")
print(pd_df['word'][83])
print(text_list[83])
print(text_list[82])
print(text_list[202])

for text in tqdm(text_list):
    raw_input_ids = tokenizer.encode(text)
    input_ids = [tokenizer.bos_token_id] + raw_input_ids + [tokenizer.eos_token_id]
    print(len(input_ids))
    try:
        question_ids = model.generate(torch.tensor([input_ids]),  num_beams=4,  max_length=1000000,  eos_token_id=1)
        quiz_list.append(tokenizer.decode(question_ids.squeeze().tolist(), skip_special_tokens=True))
    except:
        quiz_list.append("")
print("-----------generation suc--------------")

quiz_df = pd_df[['article_id','word']]
quiz_df.insert(2,'question',quiz_list)
quiz_df.rename(columns = {'word' : 'answer'}, inplace = True)
quiz_df = quiz_df[['answer','question','article_id']]
quiz_df.to_sql(name='news_quiz', con=db_connection, if_exists='append',index=False)  
os.close(0)
