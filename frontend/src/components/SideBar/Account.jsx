import http from "api/commonHttp";
import React, { useEffect, useState } from "react";
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
  const provider = useSelector((state) => state.user.provider);
  const unlinkToken = useSelector((state) => state.user.unlinkToken);
  
  const logout = () => {
    if(provider==="kakao") {
      console.log(unlinkToken);

      fetch('https://kapi.kakao.com/v1/user/logout', {
          method: 'POST',
          headers: {
              'Content-Type': 'application/x-www-form-urlencoded', 
              'Authorization': 'Bearer ' + unlinkToken
          },
      })
      .then(response => {
      }).catch(error => {
          console.error(error);
      });
  } 
    dispatch(initUser());
    navigate('/');
  }
  
  const imgUrl = "https://visiblehand-bucket.s3.ap-northeast-2.amazonaws.com/user_default.png";
  const [profileImg, setProfileImg] = useState('');
  useEffect(() => {
    http.get('/user/profile/'+loginId,
  )
  .then(response => {
      if(response.status===200) {
          setProfileImg(response.data.profileImg);
      }
  })
  .catch(error => {
  });    
  }, []);

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
        {profileImg!=null && profileImg!=""
        ?  <img src={profileImg} alt="profileImg" width={20} height={20} style={{borderRadius: "50%"}}/>
        : <img src={imgUrl} alt="profileImg" width={20} height={20}/>
        }
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
