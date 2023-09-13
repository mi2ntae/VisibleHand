import React, { useEffect } from 'react';
import styled from 'styled-components';
import http from "api/commonHttp";
import MyPageStreak from './MyPageStreak';

export default function MyPageTopRight({userId}) {
    return (
        <TopRightContainer>
            <MyPageStreak userId={userId}></MyPageStreak>
        </TopRightContainer>
    );
}


const TopRightContainer = styled.div`
    display: flex;
    justify-content: center;
    box-sizing: border-box;
    align-items: center;
    margin-right: 50px;
    width: 43%;
`;
