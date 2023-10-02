import { Outlet } from "react-router-dom";
import SideBar from "./components/SideBar/SideBar";
import styled, { createGlobalStyle } from "styled-components";
import { useSelector } from "react-redux";
import OnBoarding from "pages/Onboarding";

function App() {
  const path = window.location.pathname;

  const notSidebar = path === '/login' || path === '/auth/kakao' || path === '/auth/google' || path === '/profile';
  const user = useSelector((state) => state.user);

  return (
    <Root>
      <GlobalStyle />
      {(user.nickname==="" || user.nickname===null || notSidebar)
      ?
      <Content>
        <Outlet />
        </Content>
      :
      <>
      <SideBar />
          <Content>
            <Outlet />
          </Content>
      </>
      }
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
