import { Background, BannerTitle } from 'styled';
import styled from 'styled-components';
import MyPageWordQuizChart from './MyPageWordQuizChart';
import http from 'api/commonHttp';
import { useState, useEffect } from 'react';

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
        <Background>
            <BannerTitle style={{fontWeight: 500}}>활동뱃지</BannerTitle>
            <MyPageWordQuizChart data={wordCnt}></MyPageWordQuizChart>
        </Background>
    );
}