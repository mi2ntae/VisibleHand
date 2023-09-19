import React from 'react';
import styled from 'styled-components';
import MyPageTabs from 'components/user/mypage/left/tabs/MyPageTabs';


export default function MyPageLeftTop({userId, tabs}) {
    
    
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