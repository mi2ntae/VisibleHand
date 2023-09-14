import React, { useEffect } from 'react';
import styled from 'styled-components';
import { useDispatch } from 'react-redux';
import { setTabNo } from '../reducer/mypageTabReducer';
import { useParams } from 'react-router';
import MyPageLeft from 'components/user/mypage/MyPageLeft';
import MyPageRight from 'components/user/mypage/MyPageRight';

export default function Mypage() {
    const userId = parseInt(useParams().userId);
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
                {/* <MyPageTop userId={params.userId}>
                </MyPageTop>
                <MyPageDown userId={params.userId}>
                </MyPageDown> */}
                <MyPageLeft userId={userId}></MyPageLeft>
                <MyPageRight userId={userId}></MyPageRight>
            </MainContainer>
        </div>
    );
}

const MainContainer = styled.div`
    display: flex;
    flex-direction: row;
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