import React, { useState, useEffect } from "react";
import QuizContent from "./QuizContent";
import styled from "styled-components";
import color from "lib/style/colorPalette";
export default function QuizSolve() {
  const [ranking, setRanking] = useState([]);
  useEffect(() => {
    setRanking([
      {
        userId: 1,
        nickname: "루기아",
        profileImg:
          "https://blog.kakaocdn.net/dn/FOcCY/btr5QAbW7Sv/iOSQ86mcRgUNUqQAf9ahRK/img.jpg",
        statusMsg: "하반기 취업왕은 나야나",
      },
      {
        userId: 1,
        nickname: "루기아",
        profileImg:
          "https://blog.kakaocdn.net/dn/FOcCY/btr5QAbW7Sv/iOSQ86mcRgUNUqQAf9ahRK/img.jpg",
        statusMsg: "하반기 취업왕은 나야나",
      },
      {
        userId: 1,
        nickname: "루기아",
        profileImg:
          "https://blog.kakaocdn.net/dn/FOcCY/btr5QAbW7Sv/iOSQ86mcRgUNUqQAf9ahRK/img.jpg",
        statusMsg: "하반기 취업왕은 나야나",
      },
    ]);
  }, []);
  return (
    <MainContainer>
      <QuizContent></QuizContent>
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
                    <div>{v.nickname}</div>
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
                  <div>{v.statusMsg}</div>
                </div>
              </RankingItem>
            ))}
          </ItemContainer>
        </RankingContainer>
        <ComboContainer>
          <div>132</div>
          <div>
            <div>연속 정답 행진!</div>
            <div>최고기록: 241</div>
          </div>
        </ComboContainer>
      </RightContainer>
    </MainContainer>
  );
}
const MainContainer = styled.div`
  display: flex;
  padding: 70px 0;
`;
const RightContainer = styled.div``;
const RankingContainer = styled.div`
  box-sizing: border-box;
  width: 468px;
  height: 516px;
  background-color: ${color.white};
  border-radius: 16px;
  border: 1px solid ${color.lightest_grey};
  padding: 20px 32px;
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
  height: 90px;
  align-items: center;
  border-bottom: 1px solid ${color.lightest_grey};
  & > div:first-child {
    width: 32px;
    font-size: 32px;
  }
  & > div:nth-child(2) {
    width: 40px;
    height: 40px;
    border-radius: 20px;
    img {
      border-radius: inherit;
      width: inherit;
      height: inherit;
      object-fit: cover;
      object-position: center center;
    }
  }
  div:nth-child(3) {
    margin-left: 12px;
    & > div:first-child {
      color: ${color.black_grey};
      font-size: 16px;
      display: flex;
      align-items: center;
      gap: 4px;
    }
    & > div:last-child {
      color: ${color.grey};
      font-size: 14px;
    }
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
  width: 468px;
  height: 194px;
  box-sizing: border-box;
  background-image: url("/icons/quiz/Banner_continuity.svg");
  display: flex;
  align-items: center;
  color: ${color.white};
  gap: 8px;
  & > div:first-child {
    font-size: 64px;
    font-weight: 600;
  }
  & > div:last-child {
    & > div:first-child {
      font-size: 24px;
    }
    & > div:last-child {
      font-size: 16px;
    }
  }
`;
