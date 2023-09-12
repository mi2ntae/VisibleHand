import React from 'react';
import styled from 'styled-components';

export default function MyPageReview() {

    return (
        <div>
            <ReviewContainer>
            </ReviewContainer>
        </div>
    );
}

const ReviewContainer = styled.div`
    display: flex;
    justify-content: flex-start;
    margin: auto;
    padding: 20px;
    box-sizing: border-box;
    background-color: purple;
    width: 100%;
`;