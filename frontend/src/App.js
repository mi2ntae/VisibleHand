import { Outlet } from "react-router-dom";
import SideBar from "./components/SideBar/SideBar";
import styled, { createGlobalStyle } from "styled-components";
import OnBoarding from "pages/Onboarding";
import { useSelector } from "react-redux";
import { Navigate, useLocation } from "react-router-dom";

function App() {
  const token = useSelector((state) => state.user.accessToken);
  const pathname = useLocation().pathname;

  const whiteList = [/^\/$/, /^\/login$/];

  return (
    <Root>
      <GlobalStyle />
      <SideBar />
      <Content>
        {token || whiteList.some((item) => item.test(pathname)) ? (
          <Outlet />
        ) : (
          <Navigate to="/login" />
        )}
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
  margin-left: 276px;
`;

export default App;
