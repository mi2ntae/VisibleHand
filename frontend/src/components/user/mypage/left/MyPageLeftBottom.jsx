import React from 'react';
import { useSelector } from 'react-redux';
import { Background } from 'styled';
import styled from 'styled-components';
export default function MyPageLeftBottom({userId, tabs}) {
    const tabNo = useSelector((state) => state.mypageTab.tabNo);

    return (
        <Background style={{minHeight: "80vh"}}>
            {tabs.map((tab) => tabNo === tab.no ? tab.content : <span></span>)}
        </Background>
    );
}