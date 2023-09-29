import React from "react";
import { styled } from "styled-components";
import color from "lib/style/colorPalette";
import Swal from "sweetalert2";
import http from "api/commonHttp";
import Test from './ProgressTimeBar'
import { useState, useEffect, useRef } from "react";
export default function QuizContent({ question, content, text, score }) {
  const letterRef = useRef([]);
  // const addToRefs=(el)=>{letterRef.current.push(el); el.id = letterRef.current.length-1}
  const correctAnswer = text.split("");
  const sendScore = (i) => {
    score(i);
  };
  const [answer, setAnswer] = useState([]);

  useEffect(() => {
    letterRef.current = [];
    let t = []; 
    for (let i = 0; i < correctAnswer.length; i++) {
      if(isSymbol(correctAnswer[i])) t.push(correctAnswer[i]);
      else t.push(-1);
    }
    setAnswer(t);
  }, []);
  const isSymbol = (ch) => {
    const symbols = "-&.()%+,:/ ";
    if(symbols.includes(ch)) return true;
    else return false;
  }

  const onKeyDown = (e, i) => {
    console.log(i)
    if(letterRef.current.length === 0 ) return;
    if(e.key==="ArrowRight") {
      for(let j=i+1; j<=letterRef.current.length-1; j++) {
        if(!isSymbol(correctAnswer[j])) { 
          letterRef.current[j].focus()
          break;
        }
      }
    }
    else if(e.key==="ArrowLeft") {
      for(let j=i-1; j>=0; j--) {
        if(!isSymbol(correctAnswer[j])) {
          letterRef.current[j].focus()
          break;
        }
      }
    }
  }
  const onChange = (e, idx) => {
    letterRef.current = [];
    if (e.target.value.length >= 1) {
      e.target.value = e.target.value.substring(e.target.value.length-1)
      let temp = [];
      for (let index = 0; index < answer.length; index++) {
        if(idx === index) temp.push(e.target.value)
        else temp.push(answer[index])
      }
      setAnswer(temp);
    }
  };

  const mark = () => {
    console.log(answer)
    console.log(answer.join("")===text)
    // if (text === answer) {
    //   //정답
    //   http
    //     .put("quiz", { userId: 1, wordId: 0, correct: true })
    //     .then(() => {
    //       Swal.fire({
    //         title: "정답입니다!",
    //         imageUrl: "/icons/quiz/ic_right.svg",
    //         showConfirmButton: true,
    //         showDenyButton: true,
    //         confirmButtonText: "다음 문제로",
    //         denyButtonText: "끝내기",
    //       }).then((result) => {
    //         if (result.isConfirmed) {
    //           alert("다음 문제로");
    //         } else if (result.isDenied) {
    //           alert("끝내기");
    //         }
    //         sendScore(1);
    //       });
    //     })
    //     .catch((err) => {
    //       alert(err);
    //     });
    // } else {
    //   //오답
    //   http
    //     .put("quiz", { userId: 1, wordId: 0, correct: true })
    //     .then(() => {
    //       Swal.fire({
    //         title: "오답입니다!",
    //         text: `(정답: ${text})`,
    //         imageUrl: "/icons/quiz/ic_wrong.svg",
    //         showConfirmButton: true,
    //         showDenyButton: true,
    //         confirmButtonText: "다음 문제로",
    //         denyButtonText: "끝내기",
    //       }).then((result) => {
    //         if (result.isConfirmed) {
    //           alert("다음 문제로");
    //         } else if (result.isDenied) {
    //           alert("끝내기");
    //         }
    //         sendScore(-1);
    //       });
    //     })
    //     .catch((err) => {
    //       alert(err);
    //     });
    // }
  };
  console.log(letterRef.current)
  return (
    <ComponentContainer>
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          width: "870px",
        }}
      >
        <Question>{question}</Question>
        <HintButton>
          <img src={"/icons/quiz/ic_bulb.svg"} />
          <button>힌트 보기</button>
        </HintButton>
      </div>
      <ContentContainer>{content}</ContentContainer>
      {/* <ProgressBar>
        <div></div>
      </ProgressBar> */}
    <Test/>
      <AnswerContainer>
        {answer.map((v, i) => (
          (v===" " ? <div ref={(el) =>{letterRef.current.push(el);}}>공백 </div> :
          <Letter
            key={i}
            onChange={(e) => onChange(e, i)}
            ref={(el) =>{letterRef.current[i] = (el);}}
            value={v!==-1 ? v : null}
            disabled={isSymbol(v)}
            onKeyDown={(e) => onKeyDown(e, i)}
          ></Letter>
          )
        ))}
      </AnswerContainer>
      <SubmitButton onClick={mark}>제출하기</SubmitButton>
    </ComponentContainer>
  );
}
const ComponentContainer = styled.div`
  margin: 0px 100px;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 870px;
`;
const Question = styled.div`
  font-weight: 600;
  font-size: 36px;
  color: ${color.black_grey};
  margin-bottom: 32px;
`;
const HintButton = styled.div`
  display: flex;
  align-items: center;
  img {
    padding-bottom: 5px;
  }
  button {
    border: none;
    background-color: transparent;
    color: ${color.primary};
    font-size: 20px;
    font-weight: 500;
  }
`;
const ContentContainer = styled.div`
  padding: 40px;
  background-color: ${color.white};
  width: 790px;
  height: 507px;
  color: ${color.black_grey};
  border: 1px solid ${color.lightest_grey};
  font-weight: 24px;
  border-radius: 16px;
`;
const SubmitButton = styled.button`
  margin-top: 56px;
  color: ${color.white};
  border: none;
  background-color: #8071fc;
  width: 208px;
  height: 60px;
  font-size: 24px;
  border-radius: 16px;
  font-weight: 600;
  &:focus {
    outline: none;
    border: none;
  }
`;
const ProgressBar = styled.div`
  margin: 16px 0;
  width: 870px;
  height: 16px;
  background-color: ${color.white};
  border-radius: 16px;
  & > div {
    width: 50%;
    background-color: ${color.secondary};
    height: inherit;
    border-radius: inherit;
  }
`;
const AnswerContainer = styled.div`
  display: flex;
  justify-content: center;
  & > input {
    margin-right: 24px;
  }
  & > input:last-child {
    margin: 0;
  }
  max-width: 500px;
`;
const Letter = styled.input`
  border: none;
  width: 92px;
  height: 92px;
  background-color: ${color.white};
  border-radius: 12px;
  font-size: 36px;
  text-align: center;
  &:focus {
    outline: none;
    width: 88px;
    height: 88px;
    background-color: ${color.white};
    border: 2px solid ${color.teritary};
  }
`;
