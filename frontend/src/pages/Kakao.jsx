import React, {useEffect} from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { setUser } from "reducer/userReducer";
import http from "api/commonHttp";

export default function Kakao() {
    const user = useSelector((state) => state.user);
    console.log(user);

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
    

  return (
    // 컴포넌트로 바꾸기
    <div>
        <strong>프로필 설정</strong><br></br>
        닉네임 <input type="text" name="nickname" placeholder='닉네임을 입력하세요.' />
        상태메시지 <input type="text" name="stateMsg" placeholder='상태메시지를 입력하세요.' />
        이메일 <input type="text" name="snsEmail" value={user.snsEmail} readOnly />
    </div>
  );
}

