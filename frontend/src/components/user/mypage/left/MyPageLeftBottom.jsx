import React from 'react';
import { useSelector } from 'react-redux';
import styled from 'styled-components';
export default function MyPageLeftBottom({userId, tabs}) {
    const tabNo = useSelector((state) => state.mypageTab.tabNo);

    return (
        <LeftBottomContainer>
            {tabs.map((tab) => tabNo === tab.no ? tab.content : <span></span>)}
        </LeftBottomContainer>
    );
}


const LeftBottomContainer = styled.div`
    background-color: white;
    min-height: 50px;
    margin-top: 1%;
    height: 90%;
    border-radius: 20px;
    box-shadow: 3px 3px 3px lightgrey;
`;