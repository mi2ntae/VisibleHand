import { Outlet } from "react-router-dom";
import SideBar from "./components/SideBar/SideBar";
import styled, { createGlobalStyle } from "styled-components";
import { useSelector } from "react-redux";
import OnBoarding from "pages/Onboarding";

function App2() {
  return (
    <Root>
      <GlobalStyle />
      <Content>
        <Outlet />
      </Content>
    </Root>
  );
}

const GlobalStyle = createGlobalStyle`
  body {
    font-family: 'Pretendard'
  }
`;

const Root = styled.div`
  display: flex;
  min-height: 100vh;
  background-color: #eff4ff;
`;

const Content = styled.div`
  flex-grow: 1;
`;

export default App2;
