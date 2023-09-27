import React from 'react';
import {useSelector} from 'react-redux';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';

function Congrats() {
  const user = useSelector((state) => state.user);
  console.log(user.nickname);

  const navigate = useNavigate();

  const moveMain = () => {
    navigate('/');
  }

  const moveUpdate = () => {
    navigate('/update');
  }

  return (
    // 예쁘게 꾸미기
    <ContentWrap>
        <Container>
          <h3>{user.nickname} 님의 회원가입을 축하합니다</h3>
          <button onClick={moveMain}>메인으로 이동</button>

          {/* //마이페이지에서 연결할 것 */}
          <button onClick={moveUpdate}>프로필 수정 페이지</button>
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
  