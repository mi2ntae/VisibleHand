import React from 'react';
import styled from 'styled-components';

export default function Mypage() {

    return (
        <div>
            <FeedContainer>
            </FeedContainer>
        </div>
    );
}

const FeedContainer = styled.div`
    display: flex;
    justify-content: flex-start;
    margin: auto;
    padding: 20px;
    box-sizing: border-box;
    background-color: red;
    width: 100%;
`;