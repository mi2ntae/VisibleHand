import React from "react";
import QuizContent from "./QuizContent";
import styled from "styled-components";
import color from "lib/style/colorPalette";
export default function QuizSolve(){
  return <MainContainer>
    <QuizContent></QuizContent>
    {/* <RankingContainer></RankingContainer> */}
  </MainContainer>
}
const MainContainer=styled.div`
  display:flex;
  padding:70px 0;
`;
const RankingContainer=styled.div`
  width:468px;
  height:516px;
  background-color: ${color.white};
  border-radius: 16px;
  border:1px solid ${color.lightest_grey};
`;