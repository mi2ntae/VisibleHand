import { Background, BannerTitle } from 'styled';
import styled from 'styled-components';
import MyPageWordQuizChart from './MyPageWordQuizChart';
import http from 'api/commonHttp';
import { useState, useEffect } from 'react';
import { Height } from '@mui/icons-material';

export default function MyPageRightBottom({userId}) {
    const [wordCnt, setWordCnt] = useState([]);

    useEffect(() => {
        http.get(`user/quiz/${userId}`)
        .then(({data}) => {
            setWordCnt(data.word.cnts)
            console.log(data.word.cnts)
        })
        .catch((err) => {
            console.log(err)
        })
    }, [])
    
    return (
        <Background style={{minHeight: "260px", maxHeight: "300px"}}>
            <BannerTitle style={{fontWeight: 500}}>퀴즈 현황</BannerTitle>
            {wordCnt.reduce((acc, cur) => {
                return acc + cur
            }, 0) > 0
            ?
            <MyPageWordQuizChart data={wordCnt}></MyPageWordQuizChart>
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
    margin-top: 30%;
`;