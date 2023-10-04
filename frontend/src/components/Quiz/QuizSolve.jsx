import React, { useState, useEffect } from "react";
import QuizContent from "./QuizContent";
import styled from "styled-components";
import color from "lib/style/colorPalette";
import http from "api/commonHttp";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import Swal from "sweetalert2";

export default function QuizSolve({ retry }) {
  const navigate = useNavigate();
  const userId = useSelector((state) => state.user.userId);
  const [ranking, setRanking] = useState([]);
  const [isCombo, setIsCombo] = useState(false);
  const [comboNum, setComboNum] = useState(0);

  // useEffect(() => {
  //   http.get(`quiz/rank`).then((res) => {
  //     setRanking(res.data);
  //   });
  // }, []);

  const [question, setQuestion] = useState("");
  const [content, setContent] = useState("");
  const [wordId, setWordId] = useState(1);
  const [text, setText] = useState(""); //퀴즈 답
  const [correct, setCorrect] = useState(0);
  const [wrong, setWrong] = useState(0);
  useEffect(() => {
    if (retry) {
      http
        .get(`quiz/retry/${userId}`)
        .then((res) => {
          if (res.data.allSolved) {
            Swal.fire({
              text: "수고하셨습니다! 모두 다시 풀었어요!",
              imageUrl: "/icons/quiz/allSolved.png",
              showConfirmButton: false,
              showDenyButton: false,
              showCancelButton: false,
              timer: 2000,
            }).then(() => {
              navigate("/news");
            });
            return;
          }
          setQuestion("다음 의미를 가진 경제용어는?");
          setContent(res.data.entries[0].meaning);
          setText(res.data.entries[0].word);
          setWordId(res.data.entries[0].wordId);
        })
        .catch((err) => alert(err));
    } else {
      http
        .get(`quiz/dict/${userId}`)
        .then((res) => {
          if (res.data.allSolved) {
            Swal.fire({
              text: "수고하셨습니다! 모두 다시 풀었어요!",
              imageUrl: "/icons/quiz/allSolved.png",
              showConfirmButton: false,
              showDenyButton: false,
              showCancelButton: false,
              timer: 2000,
            }).then(() => {
              navigate("/news");
            });
            return;
          }
          setQuestion("다음 의미를 가진 경제용어는?");
          setContent(res.data.entries[0].meaning);
          setText(res.data.entries[0].word);
          setWordId(res.data.entries[0].wordId);
        })
        .catch((err) => alert(err));
    }
  }, []);

  const score = (i, c) => {
    let newCorrect = correct;
    let newWrong = wrong;
    if (i > 0) {
      if (!isCombo) {
        setIsCombo((prev) => !prev);
      }
      setComboNum((prev) => prev + 1);
      newCorrect = correct + 1;
    } else {
      if (isCombo) {
        setComboNum(0);
        setIsCombo((prev) => !prev);
      }
      newWrong = wrong + 1;
    }
    setCorrect(newCorrect);
    setWrong(newWrong);
    if (c) {
      if (retry) {
        http
          .get(`quiz/retry/${userId}`)
          .then((res) => {
            if (res.data.allSolved) {
              Swal.fire({
                text: "수고하셨습니다! 모두 다시 풀었어요!",
                imageUrl: "/icons/quiz/allSolved.png",
                showConfirmButton: false,
                showDenyButton: false,
                showCancelButton: false,
                timer: 2000,
              }).then(() => {
                navigate("/news");
              });
              return;
            }
            setQuestion("다음 의미를 가진 경제용어는?");
            setContent(res.data.entries[0].meaning);
            setText(res.data.entries[0].word);
            setWordId(res.data.entries[0].wordId);
          })
          .catch((err) => alert(err));
      } else {
        http
          .get(`quiz/dict/${userId}`)
          .then((res) => {
            if (res.data.allSolved) {
              Swal.fire({
                text: "수고하셨습니다! 모두 다시 풀었어요!",
                imageUrl: "/icons/quiz/allSolved.png",
                showConfirmButton: false,
                showDenyButton: false,
                showCancelButton: false,
                timer: 2000,
              }).then(() => {
                navigate("/news");
              });
              return;
            }
            setQuestion("다음 의미를 가진 경제용어는?");
            setContent(res.data.entries[0].meaning);
            setText(res.data.entries[0].word);
            setWordId(res.data.entries[0].wordId);
          })
          .catch((err) => alert(err));
      }
    } else {
      const str = newCorrect > newWrong ? "Good job!!" : "분발하세요..!";
      Swal.fire({
        title: str,
        text: `맞춘 단어: ${newCorrect}개 \n틀린 단어: ${newWrong}개`,
        showConfirmButton: false,
        showDenyButton: false,
        timer: 1000,
      }).then(() => {
        navigate("/news");
      });
    }
  };
  return (
    <MainContainer>
      <QuizContent
        wordId={wordId}
        question={question}
        content={content}
        text={text}
        score={score}
        userId={userId}
      ></QuizContent>
      <RightContainer>
        <RankingContainer>
          <RankingHeader>
            <div>이 주의 퀴즈왕</div>
            <div
              style={{
                display: "flex",
                alignItems: "center",
              }}
            >
              <img src={"/icons/quiz/Clock.svg"} alt="시계" />
              대충 기간
            </div>
          </RankingHeader>
          <ItemContainer>
            {ranking.map((v, i) => (
              <RankingItem key={i}>
                <div>{i + 1}</div>
                <div>
                  <img src={v.profileImg} alt="프로필 이미지" />
                </div>
                <div>
                  <div>
                    <div className="nickname">
                      {v.nickname}
                      {i === 0 ? (
                        <img
                          src={"/icons/quiz/profile_badge_first.svg"}
                          alt="1등"
                        />
                      ) : i === 1 ? (
                        <img
                          src={"/icons/quiz/profile_badge_second.svg"}
                          alt="2등"
                        />
                      ) : i === 2 ? (
                        <img
                          src={"/icons/quiz/profile_badge_third.svg"}
                          alt="3등"
                        />
                      ) : (
                        <></>
                      )}
                    </div>
                  </div>
                  <div className="statusMsg">{v.statusMsg}</div>
                </div>
              </RankingItem>
            ))}
          </ItemContainer>
        </RankingContainer>
        <ComboContainer>
          <div>{comboNum}</div>
          <div>
            <div>
              {correct === 0 && wrong === 0
                ? "도전!!!"
                : isCombo
                ? "연속 정답 행진!!"
                : "앗차차~"}
            </div>
            <div>최고기록: 241</div>
          </div>
        </ComboContainer>
      </RightContainer>
    </MainContainer>
  );
}
const MainContainer = styled.div`
  display: flex;
  padding: 48px 0;
`;
const RightContainer = styled.div``;
const RankingContainer = styled.div`
  box-sizing: border-box;
  width: 341px;
  height: 376px;
  background-color: ${color.white};
  border-radius: 16px;
  border: 1px solid ${color.lightest_grey};
  padding: 16px 22px;
`;
const RankingHeader = styled.div`
  display: flex;
  height: 65px;
  align-items: center;
  & > div:first-child {
    font-size: 20px;
    color: ${color.black_grey};
    font-weight: 600;
  }
  justify-content: space-between;
`;
const RankingItem = styled.div`
  display: flex;
  height: 48px;
  align-items: center;
  border-bottom: 1px solid ${color.lightest_grey};
  & > div:first-child {
    width: 22px;
    font-size: 22px;
  }
  & > div:nth-child(2) {
    width: 30px;
    height: 30px;
    border-radius: 15px;
    img {
      border-radius: inherit;
      width: inherit;
      height: inherit;
      object-fit: cover;
      object-position: center center;
    }
  }
  div:nth-child(3) {
    margin-left: 8px;
  }
  .nickname {
    color: ${color.black_grey};
    font-size: 12px;
    display: flex;
    align-items: center;
    gap: 4px;
  }
  .statusMsg {
    color: ${color.grey};
    font-size: 10px;
  }
`;
const ItemContainer = styled.div`
  & > div:last-child {
    border-bottom: none;
  }
`;

const ComboContainer = styled.div`
  padding: 0px 32px;
  margin-top: 32px;
  width: 341px;
  height: 141px;
  box-sizing: border-box;
  background-image: url("/icons/quiz/Banner_continuity.svg");
  background-size: cover;
  display: flex;
  align-items: center;
  color: ${color.white};
  gap: 8px;
  & > div:first-child {
    font-size: 46px;
    font-weight: 600;
  }
  & > div:last-child {
    & > div:first-child {
      font-size: 18px;
    }
    & > div:last-child {
      font-size: 12px;
    }
  }
`;
