import React from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import NavBar from "./NavBar";
import Account from "./Account";

export default function SideBar() {
  return (
    <Bar>
      <Link to="/news">
        <img src="/logo.svg" alt="Visible Hand" />
      </Link>
      <NavBar />
      <Account />
    </Bar>
  );
}

const Bar = styled.div`
  width: 276px;
  height: 100vh;
  border-radius: 0px 16px 16px 0px;
  display: flex;
  flex-direction: column;
  padding: 32px;
  box-sizing: border-box;
  justify-content: space-between;
  gap: 64px;
  position: fixed;
  background-color: white;
`;
