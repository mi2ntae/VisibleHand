import React from 'react';
import styled from 'styled-components';

export default function MyPageLeftBottom({userId}) {
    return (
        <LeftBottomContainer>
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