import React, { useEffect, useState } from 'react';
import style from './WordCloudTabs.module.css'
import styled from 'styled-components';
import { useDispatch } from 'react-redux';
import { setKind } from 'reducer/wordCloudReducer';

export default function WordCloudTabs() {
  const types = [
    {kor : "금융", eng : "FINANCE"},
    {kor : "증권", eng : "STOCK"},
    {kor : "산업/재계", eng : "INDUSTRY"},
    {kor : "중기/벤처", eng : "VENTURE"},
    {kor : "부동산", eng : "REAL_ESTATE"},
    {kor : "글로벌 경제", eng : "GLOBAL"},
    {kor : "생활경제", eng : "LIVING"},
    {kor : "경제 일반", eng : "GENERAL"}];

  const dispatch = useDispatch()
  const [selectedIdx, setSelectedIdx] = useState(0);
  const onClick = (idx) => {
    setSelectedIdx(idx);
    dispatch(setKind(types[idx].eng));
  }

  useEffect(()=>{
    dispatch(setKind(types[0].eng));
  },[])

  return (
      <Container>
        {types.map((type, idx) =>
          <span key={idx} className={`${style.tab} ${idx === selectedIdx ? style.selected : null}`} onClick={()=> onClick(idx)}>{type.kor}</span>
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