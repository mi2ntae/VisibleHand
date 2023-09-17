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
`;