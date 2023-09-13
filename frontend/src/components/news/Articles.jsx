import React from 'react';
import styled from 'styled-components';

export default function Articles() {
    return (
        <Container>뉴스</Container>
    );
}

const Container = styled.div`
width : 38%;
border : 1px solid white;
background-color: white;
border-radius : 1rem;
box-shadow: 4.0px 8.0px 8.0px hsl(0deg 0% 0% / 0.38);
margin-top: 1%;
`;
