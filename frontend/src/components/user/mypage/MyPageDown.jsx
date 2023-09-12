import React from 'react';
import styled from 'styled-components';
import MyPageTabs from './down/tabs/MyPageTabs';
import MyPageFeed from "./down/MyPageFeed"
import { useSelector } from 'react-redux';
import MyPageScrap from './down/MyPageScrap';
import MyPageReview from './down/MyPageReview';

export default function MyPageDown() {
    const tabNo = useSelector((state) => state.mypageTab.tabNo);
    const tabs = [
        {no: 0, tabName: "내 피드", check: true, content: <MyPageFeed/>, },
        {no: 1, tabName: "스크랩한 기사", check: false, content: <MyPageScrap/>}, 
        {no: 2, tabName: "오답 노트", check: false, content: <MyPageReview/>}];
    return (
        <DownContainer>
            <MyPageTabs tabs={tabs}></MyPageTabs>
            <HLine></HLine>
            {tabs.map((tab) =>
                tabNo === tab.no ? tab.content : <div></div>)}
        </DownContainer>
    );
}


const DownContainer = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    margin: auto;
    margin-top: 40px;
    border-radius: 20px;
    box-sizing: border-box;
    background-color: white;
    width: 85%;
    min-height: 250px;
    max-width: 1240px;
    min-width: 800px;
    box-shadow: 3px 3px 3px lightgrey;
`;

const HLine = styled.hr`
    margin: 0;
    margin-top: 5px;
    border: 0;
    width: 100%;
    height: 3px;
    background: #EFF4FF;
`