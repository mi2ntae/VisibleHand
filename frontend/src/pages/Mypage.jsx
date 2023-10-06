import React, { useEffect, useState } from "react";
import styled from "styled-components";
import {
  setKeyword,
  setSearchType,
  setTabNo,
} from "../reducer/mypageTabReducer";
import { useParams } from "react-router";
import MyPageLeft from "components/user/mypage/MyPageLeft";
import MyPageRight from "components/user/mypage/MyPageRight";
import { setUserId } from "../reducer/mypageTabReducer";
import { useDispatch, useSelector } from "react-redux";

export default function Mypage() {
  const dispatch = useDispatch();
  const userId = useSelector((state) => state.mypageTab.userId);

  useEffect(() => {
    return () => {
      dispatch(setTabNo(0));
      dispatch(setKeyword(""));
      dispatch(setSearchType(0));
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <div
      style={{ display: "flex", padding: "0px 80px 10px 80px", gap: "88px" }}
    >
      {/* <MyPageTop userId={params.userId}>
            </MyPageTop>
            <MyPageDown userId={params.userId}>
            </MyPageDown> */}
      {userId === 0 ? <div></div> : <MyPageLeft></MyPageLeft>}
      {userId === 0 ? <div></div> : <MyPageRight></MyPageRight>}
    </div>
  );
}
