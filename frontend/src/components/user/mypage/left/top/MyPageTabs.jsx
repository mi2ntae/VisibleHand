import React from 'react';
import "components/user/mypage/css/Tab.css";
import MyPageTab from './MyPageTab';
import styled from 'styled-components';

export default function MyPageTabs({tabs}) {
    return (
        <TabContainer>
            <div className="radio-inputs">
                {tabs.map((tab, index) => (
                    <MyPageTab key={index} no={tab.no} tabName={tab.tabName} isFirst={tab.check}></MyPageTab>
                ))}
            </div>
        </TabContainer>
    );
}

const TabContainer = styled.div`
    // min-height: 250px;
    // max-width: 1240px;
    min-height: 45px;
    // width: 35%;
    display: inline-block;
    text-align: left;
`;