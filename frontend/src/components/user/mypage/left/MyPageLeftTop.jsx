import React from 'react';
import styled from 'styled-components';
import MyPageTabs from 'components/user/mypage/left/top/MyPageTabs';
import MypageSearch from 'components/user/mypage/left/top/MypageSearch'
import { useSelector } from 'react-redux';

export default function MyPageLeftTop({userId, tabs}) {
    const tabNo = useSelector((state) => state.mypageTab.tabNo)
    return (
        <LeftTopContainer>
            <MyPageTabs tabs={tabs}></MyPageTabs>
            {
            tabNo != 2
            ?
            <MypageSearch></MypageSearch>
            :
            <span></span>
            }
        </LeftTopContainer>
    );
}


const LeftTopContainer = styled.div`
    display: flex;
    position: relative;
`;