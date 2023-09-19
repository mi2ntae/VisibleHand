import React from 'react';
import styled from 'styled-components';
import MyPageTabs from 'components/user/mypage/left/top/MyPageTabs';
import ScrapSearch from 'components/user/mypage/left/top/ScrapSearch'

export default function MyPageLeftTop({userId, tabs}) {
    return (
        <LeftTopContainer>
            <MyPageTabs tabs={tabs}></MyPageTabs>
            <ScrapSearch></ScrapSearch>
        </LeftTopContainer>
    );
}


const LeftTopContainer = styled.div`
    display: flex;
    position: relative;
`;