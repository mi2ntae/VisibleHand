import sys, os
from multiprocessing import Pool
from concurrent.futures import ThreadPoolExecutor
from bs4 import BeautifulSoup
import requests
from selenium import webdriver
import selenium
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from webdriver_manager.chrome import ChromeDriverManager
from datetime import datetime, timedelta
import pandas as pd
from pandas import DataFrame
import time
import sqlalchemy

import concurrent.futures

# driver 실행

# 옵션 생성

# 옵션 생성
# FINANCE(259, "금융"),
#     STOCK(258, "증권"),
#     INDUSTRY(261, "산업/재계"),
#     VENTURE(771, "중기/벤처"),
#     REAL_ESTATE(260, "부동산"),
#     GLOBAL(262, "글로벌 경제"),
#     LIVING(310, "생활경제"),
#     GENERAL(263, "경제 일반");
db_connection_str = 'mysql+pymysql://ssafy:ssafy@ec2-13-124-217-139.ap-northeast-2.compute.amazonaws.com/ssafy'
# db_connection_str = 'mysql+pymysql://' + os.environ("mysql_user") +'@' + os.environ('db_host')+'/' + os.environ('mysqldbname')
db_connection = sqlalchemy.create_engine(db_connection_str)
conn = db_connection.connect()

headers = {'User-Agent' : 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36'}

sleep_sec = 0.4

sid2_list  = [258, 259, 261, 771, 260, 262, 310, 263]
# kind = { 259 :'금융' , 258: '증권' , 261 : '산업/재계' ,771 : '중기/벤처' , 260 : '부동산' , 262: '글로벌 경제' , 310 : '생활경제' , 263 : '경제 일반'}
kind = { 259 :'FINANCE' , 258: 'STOCK' , 261 : 'INDUSTRY' ,771 : 'VENTURE' , 260 : 'REAL_ESTATE' , 262: 'GLOBAL' , 310 : 'LIVING' , 263 : 'GENERAL'}
# kind = { 259 :'FINANCE'}
idx = 1
now = datetime.now() - timedelta(days=1)
date = now.strftime('%Y%m%d')
page = 1


URL = 'https://news.naver.com/main/list.naver?mode=LS2D&sid2={0}&sid1=101&mid=shm&date={1}&page={2}'.format(sid2_list[idx],date,page)

    # 창 숨기는 옵션 추가
options = webdriver.ChromeOptions()
options.add_argument("headless")
# 1. 가장먼저 초기 URL을 가지고 

    # '''croll = 
    # [{
    # url : 'https://21312#',o
    # kind : '' o 
    # firstImg : 'asdfasdfasdfa.png',o
    # title :  'testset',o
    # summary : ' ',d o
    # content : 'tetstsetset',
    # createAt : 'edfasfasdfasdf',
    # company : 'edfasfasdfasdf' o
    # },...]
    # '''
def getContent(url): 
    # url 기반 content, issue_date, editor 추출
    req = requests.get(url.replace("article","article/print"),headers = headers)
    req.encoding = None
    soup = BeautifulSoup(req.text, 'html.parser')

    content = str(soup.find('div', {'class' : 'newsct_article'}))
    date = soup.find('span',{'class' : 'media_end_head_info_datestamp_time _ARTICLE_DATE_TIME'}).text.replace("오후","PM").replace("오전","AM")
    # print(date)
    try:
        date = datetime.strptime(date, '%Y.%m.%d. %p %I:%M')
    except:
        date =  datetime.strptime(date, '%Y.%m.%d')
    # print(soup.find('span', {'class':'byline_s'}).text)
    if(soup.find('span', {'class':'byline_s'}) == None):
        editor = ""
    else :
        editor = soup.find('span', {'class':'byline_s'}).text
    # for a in range(0,len(text_head)):
        # text_head.
        # text_head.get(0)
        # if(text_head[a]=='\n'): continue

    #일단 여기까지 했다..!
    return {"issue_date" : date, 'content' : content,'editor' : editor}
 

# def check_exists_by_edit(obj):
#     try:
#         obj.find_element(By.XPATH, '//*[@id="contents"]/div[2]/p/span')
#     except Exception:
#         return Falsedata_list
#     return True

def check_exists_by_xpath(obj):
    # img 존재 여부 검사
    try:
        obj.find_element(By.XPATH, 'dl/dt[1]/a/img')
    except Exception:
        return False
    return True

def detailinfo (inputs):
    # 멀티 스레드 동작 부분
    # 
    driver = webdriver.Chrome(options=options)
    code,max_page ,name = inputs
    url_sel = 'https://news.naver.com/main/list.naver?mode=LS2D&sid2={0}&sid1=101&mid=shm&date={1}&page={2}'.format(code,date,max_page)
    # 먼저 url, kind, firstImg
    driver.get(url_sel)
    list_parent = driver.find_element(By.XPATH,'/html/body/div[1]/table/tbody/tr/td[2]/div/div[2]')
    list_news_title = list_parent.find_elements(By.TAG_NAME,'ul')
    data_list = []
    # print(len(list_news_title))
    for news_title in list_news_title:
        
        # print(url_sel + " dtdtdtdtdt")
        li_lists  = news_title.find_elements(By.TAG_NAME, 'li')
        # print("li_lists size : "+ str(len(li_lists)))
        for li in li_lists:
            init_data = {"kind" : name,}
            # print(li)
            # 이미지, URL, 제목 얻음
            if check_exists_by_xpath(li) : 
                firstimg = li.find_element(By.XPATH, 'dl/dt[1]/a/img')
                init_data['thumbnail'] = firstimg.get_property("src")
                init_data['url'] = li.find_element(By.XPATH, 'dl/dt[2]/a').get_property("href")
                init_data['title'] = li.find_element(By.XPATH, 'dl/dt[2]/a').text
            else:
                init_data['thumbnail'] = ""
                init_data['url'] = li.find_element(By.XPATH, 'dl/dt/a').get_property("href")
                init_data['title'] = li.find_element(By.XPATH, 'dl/dt/a').text

            # if check_exists_by_edit(li):
            #     editor = li.find_element(By.XPATH, '//*[@id="contents"]/div[2]/p/span')
            #     print(editor)
            #     init_data['editor'] = editor.get_property("text")
            # else:
            #     init_data['editor'] = ""
            init_data['summary'] = li.find_element(By.XPATH,'dl/dd/span[1]').text
            init_data['company'] = li.find_element(By.XPATH,'dl/dd/span[2]').text
            # print(init_data['url'])
            init_data.update(getContent(init_data['url']))
            # print(init_data)
            data_list.append(init_data)
            del init_data
            # print(len(data_list))
    df = pd.DataFrame.from_dict(data_list)
    del data_list
    # print(str(max_page)+ " list size : " + df.__len__)
    df.drop_duplicates(subset=['title'])
    # print(len(df))
    save_Data(df)
    del df
    # df = pd.DataFrame.from_dict(detail)
    # df.drop_duplicates(subset=['title'])
    # # print(len(df))
    # save_Data(df)
    driver.quit()
    del driver
    time.sleep(1)
    
def save_Data(data:DataFrame):

    data.to_sql(name='article', con=db_connection, if_exists='append',index=False)  
    conn.commit()

def do_process_with_thread_crawl(kind):
    # 종목을 프로세스마다 할당해준다.
    code,name  = kind
    
    driver_p = webdriver.Chrome(options=options)
    print("process kind : "+name)
    #여기서 쓰레드를 진행하기전 각각의 프로세스가 해야하는 업무가 있어야한다.
    url_sel = 'https://news.naver.com/main/list.naver?mode=LS2D&sid2={0}&sid1=101&mid=shm&date={1}&page=10000000'.format(code,date)
    driver_p.get(url_sel)
    time.sleep(1)    # print(url_sel)
    news_list = driver_p.find_element(By.XPATH,'/html/body/div[1]/table/tbody/tr/td[2]/div/div[3]')
    a = news_list.find_element(By.TAG_NAME,'strong')
    max_page = int(a.text)
    # 여기에 멀티 스레드 코드를 할당해준다. 
    # 멀티 스레드는 각 종목펼 page Num를 기준으로 멀티 스레드가 동작한다.
    do_thread_crawl(code, max_page ,name)

    driver_p.close()
    del driver_p
    
 
def do_thread_crawl(code, max_cnt,name):
    thread_list = []
    with ThreadPoolExecutor(max_workers=4) as executor:
        for url in range(1,max_cnt+1):
            #페이지 번호 단위로 쓰레드를 나누어 준다..!
            thread_list.append(executor.submit(detailinfo, (code, url,name)))
        for execution in concurrent.futures.as_completed(thread_list):
            print("Thread Task Finishied")
            execution.result()    
            print("Thread Task Finishied")

    print("process end")

    


    

if __name__ == "__main__":
    start_time = time.time()
    with Pool(processes=3) as pool:  
        pool.map(do_process_with_thread_crawl, kind.items())
    print("--- elapsed time %s seconds ---" % (time.time() - start_time))



