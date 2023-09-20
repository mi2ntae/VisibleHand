import React, { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import styled from 'styled-components';
import { setKeyword } from 'reducer/mypageTabReducer';

export default function Mypage() {
    const dispatch = useDispatch();

    // useEffect(() => {
    //     return () => {
    //         dispatch(setKeyword(""));
    //     }
    // }, [])

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