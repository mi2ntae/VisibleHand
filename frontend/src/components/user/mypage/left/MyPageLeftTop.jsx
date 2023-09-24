import React from 'react';
import styled from 'styled-components';
import MyPageTabs from 'components/user/mypage/left/top/MyPageTabs';
import MypageSearch from 'components/user/mypage/left/top/MypageSearch'

export default function MyPageLeftTop({userId, tabs}) {
    return (
        <LeftTopContainer>
            <MyPageTabs tabs={tabs}></MyPageTabs>
            <MypageSearch></MypageSearch>
        </LeftTopContainer>
    );
}


const LeftTopContainer = styled.div`
    display: flex;
    position: relative;
`;