import React from 'react';
import styled from 'styled-components';
import MyPageTabs from 'components/user/mypage/left/tabs/MyPageTabs';
import MyPageFeed from "components/user/mypage/left/bottom/MyPageFeed"
import MyPageScrap from 'components/user/mypage/left/bottom/MyPageScrap';
import MyPageReview from 'components/user/mypage/left/bottom/MyPageReview';

export default function MyPageLeftTop({userId}) {
    const tabs = [
        {no: 0, tabName: "내 피드", check: true, content: <MyPageFeed/>, },
        {no: 1, tabName: "스크랩한 기사", check: false, content: <MyPageScrap/>}, 
        {no: 2, tabName: "오답 노트", check: false, content: <MyPageReview/>}];
    
        return (
        <LeftTopContainer>
            <MyPageTabs tabs={tabs}></MyPageTabs>
        </LeftTopContainer>
    );
}


const LeftTopContainer = styled.div`
    // flex-direction: row;
    justify-content: flex-start;
    // min-height: 250px;
    // max-width: 1240px;
    min-height: 50px;
    height: 50px;
    width: 100%;
`;

const HLine = styled.hr`
    margin: 0;
    margin-top: 5px;
    border: 0;
    width: 100%;
    height: 3px;
    background: #EFF4FF;
`