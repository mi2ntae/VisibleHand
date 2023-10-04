import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { setUserId } from "reducer/mypageTabReducer";
import styled from "styled-components";

export default function Account() {
  const dispatch = useDispatch();
  const loginId = useSelector((state) => state.user.userId);
  const nickname = useSelector((state) => state.user.nickname);
  const navi = useNavigate();

  const move = (e) => {
    e.stopPropagation();
    e.preventDefault();
    dispatch(setUserId(loginId));
    navi(`/mypage`);
  }
  return (
    <div style={{ gap: "240px" }}>
      <hr style={{ border: "0.5px solid #EDEDED", margin: "24px 0px" }} />
      <StyledLink onClick={(e) => move(e)}>
        <img src="/icons/header/ic_dictionary.svg" alt="dictionary" />
        {nickname}
      </StyledLink>
    </div>
  );
}

const StyledLink = styled(Link)`
  display: flex;
  gap: 14px;
  align-items: center;
  text-decoration: none;
  color: #35383b;
  font-size: 1rem;
`;
