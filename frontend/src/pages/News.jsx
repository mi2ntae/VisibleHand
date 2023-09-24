import React, { useState } from 'react';
import styled from 'styled-components';
import WordCloud from '../components/News/WordCloud';
import Articles from '../components/News/Articles';

export default function News() {
    return (
            <Container>
                <WordCloud />
                <Articles />
            </Container>
    );
}

const Container = styled.div`
display : flex;
padding : 2% 3% 2%;
height: 100vh;
box-sizing: border-box;
justify-content : space-between;
`;


