import React from 'react';
import {useSelector} from 'react-redux';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';

function Congrats() {
  const user = useSelector((state) => state.user);
  console.log(user.nickname);

  const navigate = useNavigate();

  const onClick = () => {
    navigate('/');
  }

  return (
    <ContentWrap>
        <Container>
          <h3>{user.nickname} 님의 회원가입을 축하합니다</h3>
          <button onClick={onClick}>메인으로 이동</button>
        </Container>
    </ContentWrap>
  );
}

export default Congrats;

const ContentWrap = styled.div`
    position: relative;
    height: 100vh;
`;
const Container = styled.div`
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
`;
  