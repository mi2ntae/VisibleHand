import React, { useCallback } from "react";
import { styled } from "styled-components";
import color from "lib/style/colorPalette";
import Swal from "sweetalert2";
import http from "api/commonHttp";
import ProgressTimeBar from "./ProgressTimeBar";
import { useState, useEffect, useRef } from "react";
export default function QuizContent({
  wordId,
  question,
  content,
  text,
  score,
  userId,
}) {
  const [answer, setAnswer] = useState([]);
  const letterRef = useRef([]);
  const correctAnswer = text.split("");
  const [hint, setHint] = useState("");
  const [time, setTime] = useState(false);
  useEffect(() => {
    letterRef.current = [];
    let t = []; 
    for (let i = 0; i < correctAnswer.length; i++) {
      if(isSymbol(correctAnswer[i])) t.push(correctAnswer[i]);
      else t.push(-1);
    }
    setAnswer(t);
    setTime(true)
    makeHint();
  }, [text]);
  useEffect(() => {
    letterRef.current[0]?.focus();
     // Focus on the first element if it exists
  }, [letterRef.current.length]); 
  console.log(answer)
  console.log(text)
  const makeHint = () => {
    const cho = [
      "ㄱ",
      "ㄲ",
      "ㄴ",
      "ㄷ",
      "ㄸ",
      "ㄹ",
      "ㅁ",
      "ㅂ",
      "ㅃ",
      "ㅅ",
      "ㅆ",
      "ㅇ",
      "ㅈ",
      "ㅉ",
      "ㅊ",
      "ㅋ",
      "ㅌ",
      "ㅍ",
      "ㅎ",
    ];
    let result = "";
    for (let i = 0; i < text.length; i++) {
      let code = text.charCodeAt(i) - 44032;
      if (text.charCodeAt(i) >= 65 && text.charCodeAt(i) <= 122) {
        result += "?";
        continue;
      }
      if (code > -1 && code < 11172) result += cho[Math.floor(code / 588)];
      else result += text.charAt(i);
    }
    setHint(result);
  };
  const sendScore = (i, c) => {
    score(i, c);
  };

  const isSymbol = (ch) => {
    const symbols = "-&.()%+,:/ ";
    if(symbols.includes(ch)) return true;
    else return false;
  }

  const onKeyDown = (e, i) => {
    console.log(e.key)
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
    else if(e.key==="Enter") {
      mark()
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
    setTime(false)
    if (text === answer.join("")) {
      //정답
      http
        .put("quiz", { userId: userId, wordId: wordId, correct: true })
        .then(() => {
          Swal.fire({
            title: "정답입니다!",
            imageUrl: "/icons/quiz/ic_right.svg",
            width: 600,
            showConfirmButton: true,
            confirmButtonColor: color.primary,
            showDenyButton: true,
            confirmButtonText: "다음 문제로",
            denyButtonText: "끝내기",
          }).then((result) => {
            if (result.isConfirmed) {
              sendScore(1, true);
            } else if (result.isDenied) {
              sendScore(1, false);
            }
          });
        })
        .catch((err) => {
          alert(err);
        });
    } else {
      //오답
      http
        .put("quiz", { userId: userId, wordId: wordId, correct: false })
        .then(() => {
          Swal.fire({
            title: "오답입니다!",
            text: `(정답: ${text})`,
            width: 600,
            imageUrl: "/icons/quiz/ic_wrong.svg",
            showConfirmButton: true,
            confirmButtonColor: color.primary,
            showDenyButton: true,
            confirmButtonText: "다음 문제로",
            denyButtonText: "끝내기",
          }).then((result) => {
            if (result.isConfirmed) {
              sendScore(-1, true);
            } else if (result.isDenied) {
              sendScore(-1, false);
            }
          });
        })
        .catch((err) => {
          alert(err);
        });
    }
    setAnswer([]);
    letterRef.current=[]
  };
  return (
    <ComponentContainer>
      <div
        style={{
          display: "flex",
          alignItems: "center",
          justifyContent: "space-between",
          width: "870px",
          marginBottom: "32px",
        }}
      >
        <Question>{question}</Question>
        <div>
          <HintButton>
            <img src={"/icons/quiz/ic_bulb.svg"} />
            <button>힌트 보기</button>
            <div className="hint">
              <div></div>
              <div>힌트: {hint}</div>
            </div>
          </HintButton>
        </div>
      </div>
      <ContentContainer>{content}</ContentContainer>
      {/* <ProgressBar>
        <div></div>
      </ProgressBar> */}
      <ProgressTimeBar mark={mark} time={time}></ProgressTimeBar>
      <AnswerContainer>
        {answer.map((v, i) => (
          (v===" " ? <div ref={(el) =>{letterRef.current.push(el);}} style={{width:'35px'}}></div> :
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
  display: flex;
  height: max-content;
  align-items: center;
  font-weight: 600;
  font-size: 36px;
  color: ${color.black_grey};
`;
const HintButton = styled.div`
  display: flex;
  align-items: center;
  position: relative;
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
  .hint {
    visibility: hidden;
    transition: opacity 1s ease-in-out;
    position: absolute;
    top: 16px;
    left: 20px;
    & > div:first-child {
      width: 0;
      height: 0;
      position: relative;
      left: 20px;
      background-color: transparent;
      border-left: 12px solid transparent;
      border-top: 12px solid transparent;
      border-bottom: 12px solid ${color.primary};
      border-right: 12px solid transparent;
    }
    & > div:last-child {
      background-color: ${color.primary};
      width: max-content;
      height: max-content;
      color: ${color.white};
      font-weight: 600;
      font-size: 20px;
      border-radius: 8px;
      padding: 12px;
      box-shadow: 4px 4px 4px ${color.light_grey};
    }
  }
  &:hover {
    cursor: pointer;
    .hint {
      visibility: visible;
      animation: setMotion 0.2s;
    }
    @keyframes setMotion {
      0% {
        opacity: 0;
      }
      100% {
        opacity: 1;
      }
    }
  }
`;
const ContentContainer = styled.div`
  padding: 40px;
  background-color: ${color.white};
  width: 790px;
  height: 507px;
  color: ${color.black_grey};
  border: 1px solid ${color.lightest_grey};
  font-size: 1.2rem;
  border-radius: 16px;
`;
const SubmitButton = styled.button`
  margin-top: 16px;
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
