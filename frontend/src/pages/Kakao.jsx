import React, {useEffect, useState} from 'react';
import { useDispatch, useSelector} from 'react-redux';
import { setUser } from "reducer/userReducer";
import http from "api/commonHttp";

export default function Kakao() {
  const user = useSelector((state) => state.user);
  console.log(user);
  console.log(user.snsEmail);
  console.log("닉네임1: " + user.nickname);

  const dispatch = useDispatch();   

  useEffect(() => {
      const code = new URL(window.location.href).searchParams.get("code");

      if(code) {
         http
        .get("user/auth/kakao?code=" + code)
        .then(({ data }) => {
          dispatch(setUser(data));
        })
        .catch((err) => {
          console.log(err);
        }); 
      }
      
    }, []);
  
  const [nickname, setNickname] = useState("");

  const onChange = (event) => {
    const newNickname = event.target.value;
    setUser({ ...user, nickname: newNickname });
    setNickname(newNickname);
  }

  //중복확인 해야 최종 등록 가능하도록 중복확인했는지 알 수 있는 값도 설정하기
  const isDuplicatedNick = (nickname) => {
    console.log("확인", nickname);
    http.get('/user/auth?nickname='+nickname)
    .then(({ data }) => {
      if(data.isDuplicated==0) {
        alert("사용가능한 닉네임입니다.");
      } else if(data.isDuplicated==1) (
        alert("사용할 수 없는 닉네임입니다. 다른 닉네임을 입력해주세요.")
      )
    })
    .catch(error => {
      console.log(error);
    });  
  }

  const useSubmit = (event) => {
    event.preventDefault()
    const fileInput = event.target.querySelector('input[type="file"]');
    const formData = new FormData();

    const req = {
      profile: {
        nickname: event.target.nickname.value,
        statusMsg: event.target.stateMsg.value,
      },
      snsEmail: event.target.snsEmail.value,
      provider: "kakao",
    };

    formData.append(
      'userProfileReqDto',
      new Blob([JSON.stringify(req)], {
        type: 'application/json'
      })
    )
  
    if (fileInput.files.length > 0) {
      formData.append('file', fileInput.files[0]);
    }

    setUser({ ...user, nickname: event.target.value });

    http.put('/user/auth/profile',
        formData,
        { headers: { 'content-type': `multipart/form-data` }
      })
      .then(response => {
          console.log(response);
      })
      .catch(error => {
        console.log(error);
      });   
  }

  return (
    // 컴포넌트로 바꾸기
    <form onSubmit={useSubmit}>
        <strong>프로필 설정</strong><br />
        프로필 사진 <input type="file" name="file"></input><br />
        닉네임 <input type="text" name="nickname" placeholder='닉네임을 입력하세요.' onChange={onChange}/>
        <button type="button" onClick={() => isDuplicatedNick(nickname)}>중복확인</button><br />
        상태메시지 <input type="text" name="stateMsg" placeholder='상태메시지를 입력하세요.' /><br />
        이메일 <input type="text" name="snsEmail" value={user.snsEmail} readOnly /><br />
        <button type="submit">등록</button>
    </form>
  );


}

