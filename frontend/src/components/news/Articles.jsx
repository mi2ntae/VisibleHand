import React, { useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import { useSelector } from 'react-redux';
import { black_grey, lightest_grey, white } from "lib/style/colorPalette";
import http from 'api/commonHttp';
import Article from './Article';

export default function Articles() {
  const word = useSelector((state) => state.word);
  const cloud = useSelector((state) => state.cloud);
  const [articles, setArticles] = useState([]);
  const [page, setPage] = useState(0);
  const [isLoaded, setIsLoaded] = useState(true);
  const [stop, setStop] = useState(false);
  const target = useRef(null);

  useEffect(() => {
    if (cloud.date !== "" && word.word !== "") {
      // 페이지를 초기화하고 새로운 데이터를 불러올 때 articles를 초기화합니다.
      setPage(0);
      setArticles([]);
      setIsLoaded(true);
      setStop(false);
      target.current = null;

      http.get(`/article?kind=${cloud.kind}&date=${cloud.date}&word=${word.word}&page=0`)
        .then(({ data }) => {
          setArticles(data.content);
          if (data.last) {
            setStop(true);
          } else {
            setStop(false);
          }
          setPage(1);
          setIsLoaded(true);
        })
        .catch((error) => {
          console.error("Error loading initial data:", error);
        });
    }
  }, [word, cloud]);

  useEffect(() => {
    let observer;
    if (target.current && !stop) {
      observer = new IntersectionObserver(onIntersect, {
        threshold: 0.5,
      });
      observer.observe(target.current);
    }
    return () => observer && observer.disconnect();
  }, [isLoaded]);

  useEffect(() => {
    if (!stop && isLoaded && cloud.date !== "" && word.word !== "") {
      http.get(`/article?kind=${cloud.kind}&date=${cloud.date}&word=${word.word}&page=${page}`)
        .then(({ data }) => {
          setArticles((prev) => [...prev, ...data.content]);
          setPage((prev) => prev + 1);
          setIsLoaded(false);
          if (data.last) {
            setStop(true);
          }
        })
        .catch((error) => {
          console.error("Error loading more data:", error);
        });
    }
  }, [isLoaded, page, stop]);

  const getMoreItem = () => {
    setIsLoaded(true);
  };

  const onIntersect = async ([entry], observer) => {
    if (entry.isIntersecting && !isLoaded) {
      observer.unobserve(entry.target);
      await getMoreItem();
      observer.observe(entry.target);
    }
  };
  console.log(articles.length)
  return (
    <Container>
      <Title>{word.word}</Title>
      <Row id='scrollArea'>
        {articles.length === 0 ? null :
          (articles.map((article, index) =>
            <Article
              key={index}
              articleId={article.articleId}
              title={article.title}
              thumbnail={article.thumbnail}
              kind={article.kind}
              company={article.company}
            />))
        }
        {articles.length === 0 ? "No Content" : <div ref={target} style={{ opacity:'0', height: '30px', width: '100%', backgroundColor:'red'}}></div>}
      </Row>
    </Container>
  );
}

const Container = styled.div`
  width: 38%;
  border: 1px solid ${lightest_grey};
  background-color: ${white};
  border-radius: 16px;
  margin-top: 1% 0;
  color: ${black_grey};
  padding: 1% 3%;
  box-sizing: border-box;
  position: relative;
`;

const Title = styled.div`
  position: absolute;
  top: 40px;
  left: 40px;
  font-size: 32px;
  font-style: normal;
  font-weight: 600;
  line-height: normal;
`;

const Row = styled.div`
  margin-top: 100px;
  overflow-y: scroll;
  max-height: 80%;
`;
