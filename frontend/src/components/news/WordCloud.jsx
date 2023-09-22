import React from 'react';
import styled from 'styled-components';
import Box from '@mui/material/Box';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import WordCloudTitle from './WordCloudTitle';
import WordCloudTabs from './WordCloudTabs';
import WordCloudWords from './WordCloudWords';




export default function WordCloud() {
    
    return (
        <Container>
            <WordCloudTitle ></WordCloudTitle>
            <WordCloudTabs></WordCloudTabs>
            <WordCloudWords></WordCloudWords>
        </Container>
        
    );
};

const Container = styled.div`
width: 60%;
`;

