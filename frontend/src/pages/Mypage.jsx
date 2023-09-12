import React, { useEffect } from 'react';
import MyPageDown from '../components/user/mypage/MyPageDown';
import MyPageTop from '../components/user/mypage/MyPageTop';
import styled from 'styled-components';
import { useDispatch } from 'react-redux';
import { setTabNo } from '../reducer/mypageTabReducer';

export default function Mypage() {
    const dispatch = useDispatch();

    useEffect(() => {
        return() => {
            dispatch(setTabNo(0));
        }
    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])

    return (
        <div>
            <MainContainer>
                <MyPageTop>
                </MyPageTop>
                <MyPageDown>
                </MyPageDown>
            </MainContainer>
        </div>
    );
}

const MainContainer = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    margin: auto;
    padding: 20px;
    box-sizing: border-box;
    width: 100%;
    height: 100%
    max-width: 1240px;
    min-width: 800px;
    min-height: calc(100vh - 160px);
`;