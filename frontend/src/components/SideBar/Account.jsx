import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { setUserId } from "reducer/mypageTabReducer";
import { initUser } from "reducer/userReducer";
import styled from "styled-components";

export default function Account() {
  const dispatch = useDispatch();
  const loginId = useSelector((state) => state.user.userId);
  const nickname = useSelector((state) => state.user.nickname);
  const navigate = useNavigate();

  const logout = () => {
    dispatch(initUser());
    navigate('/');
  }

  const move = (e) => {
    e.stopPropagation();
    e.preventDefault();
    dispatch(setUserId(loginId));
    navigate(`/mypage`);
  }
  return (
    <div style={{ gap: "240px" }}>
      <hr style={{ border: "0.5px solid #EDEDED", margin: "24px 0px" }} />
      <StyledLink onClick={(e) => move(e)}>
        <img src="/icons/header/ic_dictionary.svg" alt="dictionary" />
        {nickname}
      </StyledLink>
      {nickname!=null && nickname!="" ?
      <Button onClick={() => logout()}>로그아웃</Button>
      : null}
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

const Button = styled.button`
    position: absolute;
    transform: translate(170%, -80%);
    cursor: pointer;
    font-size: 10px;
    letter-spacing: 2px;
    font-weight: bold;
    padding: 0.7em 2em;
    border: none;
    background: white;
    color: #ccc;
    &:hover {
        color: black;
        transition: 0.5s;
      }
`
