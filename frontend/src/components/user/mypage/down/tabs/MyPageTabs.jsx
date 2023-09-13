import React from 'react';
import "components/user/mypage/css/Tab.css";
import MyPageTab from './MyPageTab';

export default function MyPageTabs({tabs}) {
    return (
        <div class="radio-inputs">
            {tabs.map((tab, index) => (
                <MyPageTab key={index} no={tab.no} tabName={tab.tabName} isFirst={tab.check}></MyPageTab>
            ))}
        </div>
        
    );
}