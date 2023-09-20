import React from "react";
import { styled } from "styled-components"
import color from "lib/style/colorPalette";
export default function QuizContent(){
  const question="질문 예시?";
  const content="포켓몬 세계에 온 것을 환영한다. 내 이름은 오박사. 모두가 포켓몬 박사라고 부르지!이 세계에는 포켓몬스터, 줄여서 포켓몬이라고 불리는 신비한 생명체들이 도처에 살고 있다.자네는 남자인가?아니면 여자인가?자네의 이름은?";
  const text="스타팅포켓몬";
  const correctAnswer=text.split('');
  return <ComponentContainer>
  <div style={{
    display:"flex",
    justifyContent:"space-between",
    width:"870px",
  }}>
    <Question>{question}</Question>
    <HintButton>
      <img src={"/icons/quiz/ic_bulb.svg"}/>
      <button>힌트 보기</button>
    </HintButton>
  </div>
    <ContentContainer>{content}</ContentContainer>
    <ProgressBar>
      <div></div>
    </ProgressBar>
    <AnswerContainer>
      {
        correctAnswer.map((v,i)=>
          <Letter key={i} maxlength="1"></Letter>
        )
      }
    </AnswerContainer>
    <SubmitButton>제출하기</SubmitButton>
  </ComponentContainer>
}
const ComponentContainer=styled.div`
  margin:0px 100px;
  display:flex;
  flex-direction: column;
  align-items: center;
  width:870px;
`;
const Question=styled.div`
  font-weight:600;
  font-size:36px;
  color:${color.black_grey};
  margin-bottom:32px;
`;
const HintButton=styled.div`
  display:flex;
  align-items:center;
  img{
    padding-bottom:5px;
  }
  button{
    border:none;
    background-color: transparent;
    color:${color.primary};
    font-size:20px;
    font-weight:500;
  }
`;
const ContentContainer=styled.div`
  padding:40px;
  background-color: ${color.white};
  width:790px;
  height:507px;
  color:${color.black_grey};
  border:1px solid ${color.lightest_grey};
  font-weight:24px;
  border-radius: 16px;
`;
const SubmitButton=styled.button`
  margin-top:56px;
  color:${color.white};
  border:none;
  background-color:#8071FC;
  width:208px;
  height:60px;
  font-size:24px;
  border-radius: 16px;
  font-weight:600;
  &:focus{
    outline:none;
    border:none;
  }
`;
const ProgressBar=styled.div`
  margin:16px 0;
  width:870px;
  height:16px;
  background-color: ${color.white};
  border-radius: 16px;
  &>div{
    width:50%;
    background-color:${color.secondary};
    height:inherit;
    border-radius: inherit;
  }
`;
const AnswerContainer=styled.div`
  display:flex;
  justify-content: center;
  &>input{
    margin-right:24px;
  }
  &>input:last-child{
    margin:0;
  }
`;
const Letter=styled.input`
  border:none;
  width:92px;
  height:92px;
  background-color: ${color.white};
  border-radius:12px;
  font-size:36px;
  text-align: center;
  &:focus{
    outline:none;
    width:88px;
    height:88px;
    background-color: ${color.white};
    border:2px solid ${color.teritary};
  }
`;