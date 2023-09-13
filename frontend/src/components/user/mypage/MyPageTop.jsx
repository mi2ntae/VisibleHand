import React from 'react';
import styled from 'styled-components';
import MypageTopLeft from "./top/MyPageTopLeft"
import MyPageTopRight from './top/MyPageTopRight';

export default function MyPageTop({userId}) {
    return (
        <TopContainer>
            <MypageTopLeft userId={userId}>
            </MypageTopLeft>
            <MyPageTopRight userId={userId}>
            </MyPageTopRight>
        </TopContainer>
    );
}


const TopContainer = styled.div`
    display: flex;
    margin: auto;
    margin-top: 60px;
    border-radius: 20px;
    box-sizing: border-box;
    background-color: white;
    width: 85%;
    min-height: 300px;
    max-width: 1240px;
    min-width: 800px;
    box-shadow: 3px 3px 3px lightgrey;
`;
