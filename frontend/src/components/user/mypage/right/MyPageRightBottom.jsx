import { Background, BannerTitle } from 'styled';
import styled from 'styled-components';
import MyPageWordQuizChart from './MyPageWordQuizChart';
import MyPageArticleQuizChart from './MyPageArticleQuizChart';
import http from 'api/commonHttp';
import { useState, useEffect } from 'react';
import { Height } from '@mui/icons-material';

export default function MyPageRightBottom({userId}) {
    const [wordCnt, setWordCnt] = useState([]);
    const [articleCnt, setArticleCnt] = useState([]);

    useEffect(() => {
        http.get(`user/quiz/${userId}`)
        .then(({data}) => {
            setWordCnt(data.word.cnts)
            setArticleCnt(data.article.cnts)
        })
        .catch((err) => {
            console.log(err)
        })
    }, [])
    
    return (
        <Background style={{padding: "1.5em", minHeight: "150px", maxHeight: "300px"}}>
            <BannerTitle style={{fontWeight: 500, marginBottom: 20}}>퀴즈 현황</BannerTitle>
            {wordCnt.reduce((acc, cur) => {
                return acc + cur
            }, 0) > 0
            ?
            <ChartDiv>
                <Div>
                    <Label>단어퀴즈</Label>
                    <MyPageWordQuizChart data={wordCnt}></MyPageWordQuizChart>
                </Div>
                <Div>
                    <Label>기사퀴즈</Label>
                    <MyPageArticleQuizChart data={articleCnt}></MyPageArticleQuizChart>
                </Div>
                
            </ChartDiv>
            
            :
            <NoContent>
                퀴즈를 풀지 않았어요!
            </NoContent>
        }
            
        </Background>
    );
}

const NoContent = styled.span`
    font-size: 25px;
    font-weight: 800;
    color: gray;
    margin-left: 20px;
    display: inline;
    text-align: center;
    margin-top: 10%;
`;

const ChartDiv = styled.div`
    display: flex;
    justify-content: center;
    gap: 2em;
`;

const Div = styled.div`
    justify-content: center;
    gap: 2em;
`;

const Label = styled.div`
    justify-content: center;
    text-align: center;
    margin-bottom: 5%;
`;