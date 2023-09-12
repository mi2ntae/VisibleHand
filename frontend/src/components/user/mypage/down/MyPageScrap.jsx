import React from 'react';
import styled from 'styled-components';

export default function MyPageScrap() {

    return (
        <div>
            <ScrapContainer>
            </ScrapContainer>
        </div>
    );
}

const ScrapContainer = styled.div`
    display: flex;
    justify-content: flex-start;
    margin: auto;
    padding: 20px;
    box-sizing: border-box;
    background-color: green;
    width: 100%;
`;