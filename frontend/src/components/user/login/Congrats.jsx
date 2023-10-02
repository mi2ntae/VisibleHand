import React from 'react';
import {useSelector} from 'react-redux';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import style from './Congrats.css';

function Congrats() {
  const user = useSelector((state) => state.user);

  const navigate = useNavigate();

  const moveMain = () => {
    navigate('/news');
  }

  const moveUpdate = () => {
    navigate('/update');
  }

  return (
    <ContentWrap>
        <Container>
          <div class="msg-container">
            <span class="msg">회원가입이 완료되었습니다</span>
            <br></br>
            <span class="msg2">{user.nickname} 님의 회원가입을 축하합니다 🎉</span>
            <br></br>
            <br></br>
            <Button onClick={moveMain}>MAIN</Button>
            <br></br>
          </div>
          </Container>

          <Container2>
          <div class="rocket">
            <div class="rocket-body">
              <div class="body"></div>
              <div class="fin fin-left"></div>
              <div class="fin fin-right"></div>
              <div class="window"></div>
            </div>
            <div class="exhaust-flame"></div>
            <ul class="exhaust-fumes">
              <li></li>
              <li></li>
              <li></li>
              <li></li>
              <li></li>
              <li></li>
              <li></li>
              <li></li>
              <li></li>
            </ul>
            <ul class="star">
              <li></li>
              <li></li>
              <li></li>
              <li></li>
              <li></li>
              <li></li>
              <li></li>
            </ul>
          </div>
        </Container2>
    </ContentWrap>
  );
}

export default Congrats;

const ContentWrap = styled.div`
    position: relative;
    height: 100vh;
    background: #494A4E;
`;
const Container = styled.div`
    position: absolute;
    top: 25%;
    left: 50%;
    transform: translate(-50%, -50%);
`;
  
const Container2 = styled.div`
    position: relative;
    top: 43%;
    left: 48.5%;
    transform: translate(-50%, -50%);
`;

const Button = styled.button`
    position: absolute;
    bottom: 100;
    right: 45%;
    cursor: pointer;
    font-size: 15px;
    letter-spacing: 2px;
    font-weight: bold;
    padding: 0.7em 2em;
    border: 4px solid #6A74C9;
    border-radius: 5px;
    background: white;
    color: #6A74C9;
    &:hover {
      background: #6A74C9;
      color: white;
      transition: 0.5s;
    }
`
  