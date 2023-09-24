import React from "react";
import { styled } from "styled-components";
import color from "lib/style/colorPalette";
import Swal from "sweetalert2";
import http from "api/commonHttp";
import { useState, useEffect, useRef } from "react";
export default function QuizContent({ question, content, text, score }) {
  const letterRef = useRef([]);
  const correctAnswer = text.split("");
  const sendScore = (i) => {
    score(i);
  };
  //input 조합해 answer 만들어야함-입력 시마다 answer 한 글자씩 저장할 필요 없나
  const [answer, setAnswer] = useState("스타팅포켓몬");
  // const [currentIdx, setCurrentIdx] = useState(0);
  useEffect(() => {
    let t = [];
    for (let i = 0; i < correctAnswer.length; i++) t.push(i);
    setAnswer(t);
  }, []);
  const onChange = (e, idx) => {
    if (e.target.value.length === 1) {
      if (
        letterRef.current.length > 0 &&
        idx < letterRef.current.length - 1 &&
        letterRef.current[idx + 1]
      ) {
        //useState에서 배열 특정 인덱스 값 어떻게 바꾸냐
        letterRef.current[idx + 1].focus();
      }
    }
  };
  const mark = () => {
    if (text === answer) {
      //정답
      http
        .put("quiz", { userId: 1, wordId: 0, correct: true })
        .then(() => {
          Swal.fire({
            title: "정답입니다!",
            imageUrl: "/icons/quiz/ic_right.svg",
            showConfirmButton: true,
            showDenyButton: true,
            confirmButtonText: "다음 문제로",
            denyButtonText: "끝내기",
          }).then((result) => {
            if (result.isConfirmed) {
              alert("다음 문제로");
            } else if (result.isDenied) {
              alert("끝내기");
            }
            sendScore(1);
          });
        })
        .catch((err) => {
          alert(err);
        });
    } else {
      //오답
      http
        .put("quiz", { userId: 1, wordId: 0, correct: true })
        .then(() => {
          Swal.fire({
            title: "오답입니다!",
            text: `(정답: ${text})`,
            imageUrl: "/icons/quiz/ic_wrong.svg",
            showConfirmButton: true,
            showDenyButton: true,
            confirmButtonText: "다음 문제로",
            denyButtonText: "끝내기",
          }).then((result) => {
            if (result.isConfirmed) {
              alert("다음 문제로");
            } else if (result.isDenied) {
              alert("끝내기");
            }
            sendScore(-1);
          });
        })
        .catch((err) => {
          alert(err);
        });
    }
  };
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
      <ProgressBar>
        <div></div>
      </ProgressBar>
      <AnswerContainer>
        {correctAnswer.map((v, i) => (
          <Letter
            key={i}
            onChange={(e) => onChange(e, i)}
            ref={(el) => `letterRef.current[${i}] = el`}
          ></Letter>
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
