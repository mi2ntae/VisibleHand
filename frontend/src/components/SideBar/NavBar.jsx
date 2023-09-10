import React from 'react';
import { NavLink } from 'react-router-dom';
import styled from 'styled-components';

export default function NavBar() {
    return (
        <Nav>
            <StyledLink to='/news'>
                <img src="/icons/header/ic_home.svg" alt="news" />
                뉴스
            </StyledLink>
            <StyledLink to='/feed'>
                <img src="/icons/header/ic_feed.svg" alt="feed" />
                피드
            </StyledLink>
            <StyledLink to='/dictionary'>
                <img src="/icons/header/ic_dictionary.svg" alt="dictionary" />
                사전
            </StyledLink>
            <StyledLink to='/quiz'>
                <img src="/icons/header/ic_quiz.svg" alt="quiz" />
                퀴즈
            </StyledLink>
        </Nav>
    );
}

const Nav = styled.nav`
    display: flex;
    flex-direction: column;
    gap: 40px;
    font-size: 16px;
    flex: 1;
`

const StyledLink = styled(NavLink)`
    color: #35383b;
    text-decoration: none;
    display: flex;
    justify-contents: center;
    gap: 12px;

    &.active {
    color: #6A74C9;
    font-weight: 500;
    transition: 0.1s;
    }
    &:hover {
    color: #6A74C9;
    font-weight: 500;
    transition: 0.2s;
    }
`