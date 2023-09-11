import { Outlet } from "react-router-dom";
import SideBar from "./components/SideBar/SideBar";
import styled, { createGlobalStyle } from "styled-components";

function App() {
  return (
    <Root>
      <GlobalStyle />
      <SideBar />
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
  height: 100vh;
  background-color: #EFF4FF;
`

const Content = styled.div`
  flex-grow: 1;
  margin-left: 276px;
`;

export default App;
