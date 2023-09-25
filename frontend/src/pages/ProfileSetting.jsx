import React,  {useState} from 'react';
import { useSelector} from 'react-redux';
import { setUser } from "reducer/userReducer";
import http from "api/commonHttp";
import { useNavigate } from 'react-router-dom';
import Profile from 'components/user/login/Profile';

export default function ProfileSetting() {
  const user = useSelector((state) => state.user);

  const navigate = useNavigate();

  const [nickname, setNickname] = useState("");

  const onChange = (event) => {
    const newNickname = event.target.value;
    setUser({ ...user, nickname: newNickname });
    setNickname(newNickname);
  }

  const [dupCheck, setDupCheck] = useState(false);

  const isDuplicatedNick = (nickname) => {
    http.get('/user/auth?nickname='+nickname)
    .then(({ data }) => {
      if(data.isDuplicated===0) {
        setDupCheck(true);
        alert("사용가능한 닉네임입니다.");
      } else if(data.isDuplicated===1) (
        alert("사용할 수 없는 닉네임입니다. 다른 닉네임을 입력해주세요.")
      )
      console.log("확인", nickname);
    })
    .catch(error => {
      console.log(error);
    });  
  }

  const useSubmit = (event) => {
    if(!dupCheck) {
      alert("닉네임 중복 확인을 완료해주세요.");
      return;
    }

    event.preventDefault()
    const fileInput = event.target.querySelector('input[type="file"]');
    const formData = new FormData();

    const req = {
      profile: {
        nickname: event.target.nickname.value,
        statusMsg: event.target.stateMsg.value,
      },
      snsEmail: user.snsEmail,
      provider: user.provider
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
          navigate('/signUp');
          console.log(response);
      })
      .catch(error => {
        console.log(error);
      });   
  }

  return (
    <Profile onSubmit={useSubmit}
    onChange={onChange}
    onClick={() => isDuplicatedNick(nickname)}
    value={user.snsEmail}></Profile>
  );
}
