import React from 'react';
import { BeatLoader } from 'react-spinners';
import styled from "styled-components";

function Loading() {
    return (
        <ContentWrap>
            <Container>
                <BeatLoader color="#6A74C9" />
            </Container>
        </ContentWrap>
    );
  }
  
  export default Loading;

const ContentWrap = styled.div`
    position: relative;
    height: 100%;
    width: 100%;
`;
const Container = styled.div`
    position: absolute;
    top: 50%;
    left: 47%;
`;
  