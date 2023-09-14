import React from 'react';
import styled from 'styled-components';
import MyPageRightTop from './right/MyPageRightTop';
import MyPageRightCenter from './right/MyPageRightCenter';
import MyPageRightBottom from './right/MyPageRightBottom';

export default function MyPageRight({userId}) {
    return (
        <RightContainer>
            <MyPageRightTop userId={userId}></MyPageRightTop>
            <MyPageRightCenter userId={userId}>
            </MyPageRightCenter>
            <MyPageRightBottom></MyPageRightBottom>
        </RightContainer>
    );
}


const RightContainer = styled.div`
    margin-top: 2%;
    margin-left: 5%;
    // min-height: 250px;
    // max-width: 1240px;
    min-height: 50px;
    height: 650px;
    width: 30%;
`;