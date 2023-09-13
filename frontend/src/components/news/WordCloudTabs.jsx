import React, { useState } from 'react';
import style from './WordCloudTabs.module.css'
import styled from 'styled-components';

export default function WordCloudTabs() {
  const types = ["금융","증권","산업/재계","중기/벤처","부동산","글로벌 경제","생활경제","경제 일반"];
  const [selectedIdx, setSelectedIdx] = useState(0);
  const onClick = (idx) => setSelectedIdx(idx);

  return (
      <Container>
        {types.map((type, idx) =>
          <span key={idx} className={`${style.tab} ${idx === selectedIdx ? style.selected : null}`} onClick={()=> onClick(idx)}>{type}</span>
        )}
      </Container>
  )
}

const Container = styled.div`
position: relative;
display: flex;
border-radius: 0.5rem;
box-sizing: border-box;
padding: 0.25rem;
font-size: 16 px;
line-height : 0.5;
`;