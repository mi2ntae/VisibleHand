import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { useSelector } from 'react-redux';
import { black_grey, lightest_grey, white } from "lib/style/colorPalette";
import http from 'api/commonHttp'
import Hamster from './Hamster'

export default function Article({ articleId, company, kind, title, thumbnail }) {

    return (
        <Container>
            <img src={thumbnail} alt=""  />  
            {title} {articleId}
            <hr></hr>
        </Container>
    );
}

const Container = styled.div`

`;


