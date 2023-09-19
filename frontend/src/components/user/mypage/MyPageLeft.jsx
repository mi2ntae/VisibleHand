import React from 'react';
import styled from 'styled-components';
import MyPageLeftTop from './left/MyPageLeftTop';
import MyPageLeftBottom from './left/MyPageLeftBottom';
import MyPageFeed from "components/user/mypage/left/bottom/MyPageFeed"
import MyPageScrap from 'components/user/mypage/left/bottom/MyPageScrap';
import MyPageReview from 'components/user/mypage/left/bottom/MyPageReview';
import { useSelector } from 'react-redux';

export default function MyPageLeft({userId}) {
    const loginId = useSelector((state) => state.user.userId);

    const tabs = [
        {no: 0, tabName: "내 피드", check: true, content: <MyPageFeed/>, },
        {no: 1, tabName: "스크랩", check: false, content: <MyPageScrap/>}, 
        {no: 2, tabName: "틀린 문제", check: false, content: <MyPageReview/>}];
    
    console.log(loginId, userId);
    return (
        <LeftContainer>
            <MyPageLeftTop userId={userId} tabs={loginId === userId ? tabs : tabs.slice(0, 1)}></MyPageLeftTop>
            <MyPageLeftBottom userId={userId} tabs={tabs}></MyPageLeftBottom>
        </LeftContainer>
    );
}


const LeftContainer = styled.div`
    display: flex;
    flex-direction: column;
    flex: 13;
    gap: 16px;
`;