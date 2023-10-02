import { Outlet } from "react-router-dom";
import SideBar from "./components/SideBar/SideBar";
import styled, { createGlobalStyle } from "styled-components";
import { useSelector } from "react-redux";
import OnBoarding from "pages/Onboarding";

function App() {

  return (
    <Root>
      <GlobalStyle />
      {/* {user ===  "" ? 
          <OnBoarding/> 
      :  */}
      {/* <div> */}
      <SideBar />
      <Content>
        <Outlet />
      </Content>
      {/* </div> */}
      {/* } */}
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
