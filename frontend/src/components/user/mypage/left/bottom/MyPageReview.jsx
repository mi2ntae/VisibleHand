import React, { useEffect } from 'react';
import styled from 'styled-components';
import { setKeyword } from 'reducer/mypageTabReducer';
import { useDispatch } from 'react-redux';

export default function MyPageReview() {
    const dispatch = useDispatch();

    // useEffect(() => {
    //     return () => {
    //         dispatch(setKeyword(""));
    //     }
    // }, [])

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