import React, { useEffect } from "react";
import styled from "styled-components";
import MyPageLeftTop from "./left/MyPageLeftTop";
import MyPageLeftBottom from "./left/MyPageLeftBottom";
import MyPageFeed from "components/user/mypage/left/bottom/MyPageFeed";
import MyPageScrap from "components/user/mypage/left/bottom/MyPageScrap";
import MyPageReview from "components/user/mypage/left/bottom/MyPageReview";
import { useDispatch, useSelector } from "react-redux";
import { setTabNo } from "reducer/mypageTabReducer";
import { Background } from "styled";

export default function MyPageLeft() {
  const userId = useSelector((state) => state.mypageTab.userId);
  const loginId = useSelector((state) => state.user.userId);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(setTabNo(0));
  }, []);

  const tabs = [
    { no: 0, tabName: "피드", check: true, content: <MyPageFeed /> },
    {
      no: 1,
      tabName: "스크랩",
      check: false,
      content: (
        <Background style={{ minHeight: "71vh" }}>
          <MyPageScrap />
        </Background>
      ),
    },
    {
      no: 2,
      tabName: "틀린 문제",
      check: false,
      content: (
        <Background style={{ minHeight: "71vh" }}>
          <MyPageReview />
        </Background>
      ),
    },
  ];

  return (
    <LeftContainer>
      <MyPageLeftTop
        tabs={loginId === userId ? tabs : tabs.slice(0, 1)}
      ></MyPageLeftTop>
      <MyPageLeftBottom tabs={tabs}></MyPageLeftBottom>
    </LeftContainer>
  );
}

const LeftContainer = styled.div`
  display: flex;
  flex-direction: column;
  flex: 13;
  height: 100vh;
`;
