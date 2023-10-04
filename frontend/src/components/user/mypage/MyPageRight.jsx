import React from 'react';
import styled from 'styled-components';
import MyPageRightTop from './right/MyPageRightTop';
import MyPageRightCenter from './right/MyPageRightCenter';
import MyPageRightBottom from './right/MyPageRightBottom';
import { useSelector } from 'react-redux';
export default function MyPageRight() {
    const userId = useSelector((state) => state.mypageTab.userId);
    return (
        <RightContainer>
            <MyPageRightTop userId={userId}></MyPageRightTop>
            <MyPageRightCenter userId={userId}>
            </MyPageRightCenter>
            <MyPageRightBottom userId={userId}></MyPageRightBottom>
        </RightContainer>
    );
}


const RightContainer = styled.div`
    flex: 7;
    display: flex;
    flex-direction: column;
    gap: 1.5rem;
`;