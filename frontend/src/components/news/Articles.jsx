import React, { useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import { useSelector } from 'react-redux';
import { black_grey, lightest_grey, white, teritary } from "lib/style/colorPalette";
import http from 'api/commonHttp'
import Hamster from './Hamster'
import Article from './Article';

export default function Articles() {
  const word = useSelector((state) => state.word);
  const cloud = useSelector((state) => state.cloud);
  const [articles, setArticles] = useState([]);
  const [page, setPage] = useState(0);
  const [isLoaded, setIsLoaded] = useState(false);
  const [stop, setStop] = useState(false);
  const target  = useRef(null);
  

  useEffect(()=> {
    if(cloud.date !== '') {
      http.get(`/article?kind=${cloud.kind}&date=${cloud.date}&word=${word.word}&page=0`).then(({data}) => {
        setArticles(data.content)
        if(data.last) {
          setStop(true);
        }
        else setStop(false);
        setPage(1);
      })
    }
  },[word, cloud]) 

  useEffect(() => {
    let observer;
    if (target.current && !stop) {
      // callback 함수로 onIntersect를 지정
      observer = new IntersectionObserver(onIntersect, {
        // root : document.querySelector('#scrollArea'),
        // rootMargin: '100px',
        threshold: 0.5,
      });
      observer.observe(target.current);
    }
    return () => observer && observer.disconnect();
  }, [stop, isLoaded]);

  useEffect(() => {
    // isLoaded가 true일 때 + 마지막 페이지가 아닌 경우 = 요청보내기
    if (!stop && isLoaded) {         
      http.get(`/article?kind=${cloud.kind}&date=${cloud.date}&word=${word.word}&page=${page}`)
        .then(({data}) => {
          // 받아온 데이터를 보여줄 전체 리스트에 concat으로 넣어준다
          setArticles((prev) => [...prev, ...data.content]);
          // 다음 요청할 데이터 offset 정보
          setPage((prev) => prev + 1);
          // 다음 요청 전까지 요청 그만 보내도록 false로 변경
          setIsLoaded(false);
          if (data.last) {
            setStop(true);
          }
        });
    }
  }, [isLoaded]);

    

  const getMoreItem = () => {
    // 데이터를 받아오도록 true 로 변경
    setIsLoaded(true);  
  };
  
    // callback
  const onIntersect = async ([entry], observer) => {
    // entry 요소가 교차되거나 Load중이 아니면

    if (entry.isIntersecting && !isLoaded) {

      // 관찰은 일단 멈추고
      observer.unobserve(entry.target);
      // 데이터 불러오기
      await getMoreItem();
      
      // 불러온 후 다시 관찰 실행
      observer.observe(entry.target);  
    }
  };

  console.log(articles)
    return (
        <Container>
            <Title>{word.word}</Title>
            <Row id='scrollArea'>
                {articles.length === 0 ? null:  
                    (articles.map((article, index) => 
                    <Article 
                      key={index} 
                      articleId={article.articleId} 
                      title={article.title} 
                      thumbnail={article.thumbnail} 
                    />))
                }
                {articles.length === 0 ? null: <div ref={target} style={{backgroundColor : 'tomato', height:'30px', width:'100%'}}></div>}
            </Row>
        </Container>
    );
}

const Container = styled.div`
width : 38%;
border : 1px solid ${lightest_grey};
background-color: ${white};
border-radius: 16px;
// box-shadow: 4.0px 8.0px 8.0px hsl(0deg 0% 0% / 0.38);
margin-top: 1% 0 ;
color: ${black_grey};
padding : 1% 2%;
box-sizing : border-box;
position: relative;


`;

const Title = styled.div`
position: absolute;
top :40px;
left : 30px;
font-size: 32px;
font-style: normal;
font-weight: 600;
line-height: normal;
`;


const Row = styled.div`
  margin-top: 80px;
  overflow-y: scroll;
  max-height: 600px; /* 원하는 최대 높이로 조정하세요 */
`;


