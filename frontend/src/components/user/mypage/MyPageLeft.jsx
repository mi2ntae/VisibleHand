import React from 'react';
import styled from 'styled-components';

import MyPageLeftTop from './left/MyPageLeftTop';
import MyPageLeftBottom from './left/MyPageLeftBottom';

export default function MyPageLeft({userId}) {
   

    return (
        <LeftContainer>
            <MyPageLeftTop></MyPageLeftTop>
            <MyPageLeftBottom></MyPageLeftBottom>
        </LeftContainer>
    );
}


const LeftContainer = styled.div`
    margin-top: 2%;
    margin-left: 5%;
    // flex-direction: row;
    justify-content: flex-start;
    // min-height: 250px;
    // max-width: 1240px;
    min-height: 50px;
    height: 650px;
    width: 55%;
`;