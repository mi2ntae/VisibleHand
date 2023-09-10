import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

export default function Account() {
    return (
        <StyledLink to='/mypage'>
            <img src="/icons/header/ic_dictionary.svg" alt="dictionary" />
            nickname
        </StyledLink>
    );
}

const StyledLink = styled(Link)`
    display: flex;
    gap: 14px;
    align-items: center;
    text-decoration: none;
    color: #35383B;
`