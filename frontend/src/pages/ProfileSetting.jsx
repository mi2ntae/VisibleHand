import React,  {useRef, useState} from 'react';
import { useSelector} from 'react-redux';
import { setUser } from "reducer/userReducer";
import http from "api/commonHttp";
import { useNavigate } from 'react-router-dom';
import Profile from 'components/user/login/Profile';

export default function ProfileSetting() {
  const user = useSelector((state) => state.user);

  const navigate = useNavigate();

  //프로필 사진
  const imgUrl = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png";
  const [image, setImage] = useState(imgUrl);
  const [file, setFile] = useState("");
  const inputFile = useRef();

  const onFile = (event) => {
    if(event.target.files[0]) {
      setFile(event.target.files[0]);
    } else {
      setImage(imgUrl);
      return;
    }

    const reader = new FileReader();
    reader.onload = () => {
      if(reader.readyState===2) {
        setImage(reader.result);
      }
    }
    reader.readAsDataURL(event.target.files[0]);
  }

  //드롭다운
  const [dropdown, setDropdown] = useState(false);

  const dropdownOpen = () => {
    setDropdown(!dropdown);
  };

  //닉네임, 상태메시지 유효성
  const [nickname, setNickname] = useState("");
  const [stateMsg, setStateMsg] = useState("");

  const onChangeNick = (event) => {
    const newNickname = event.target.value;
    setUser({ ...user, nickname: newNickname });
    setNickname(newNickname);
    
    if(newNickname.length>32) {
      setErrors((prev) => ({
        ...prev,
        nickname: {
          ...prev.nickname,
          invalid: true,
        },
      }));
    } else {
      setErrors((prev) => ({
        ...prev,
        nickname: {
          ...prev.nickname,
          invalid: false,
        },
      }));
    }
  }

  const onChangeMsg = (event) => {
    const newStateMsg = event.target.value;
    setStateMsg(newStateMsg);
    
    if(newStateMsg.length>128) {
      setErrors((prev) => ({
        ...prev,
        stateMsg: {
          ...prev.stateMsg,
          invalid: true,
        },
      }));
    } else {
      setErrors((prev) => ({
        ...prev,
        stateMsg: {
          ...prev.stateMsg,
          invalid: false,
        },
      }));
    }
  }

  const [errors, setErrors] = useState({
    nickname: {
        invalid: false,
        message: "닉네임은 32글자 이하여야 합니다.",
    },
    stateMsg: {
        invalid: false,
        message: "상태메시지는 128자 이하여야 합니다.",
    }
  })
 
  //닉네임 중복 체크
  const [dupCheck, setDupCheck] = useState(false);

  const isDuplicatedNick = (nickname) => {
    if(nickname==null || nickname==="") {
      alert("닉네임을 입력해주세요.");
      return;
    }

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

  //프로필 등록
  const useSubmit = (event) => {
    event.preventDefault();

    if(!dupCheck) {
      alert("닉네임 중복 확인을 완료해주세요.");
      return;
    }

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
      img={image}
      imgClick={() =>  inputFile.current.click()}
      imgRef={inputFile}
      imgChange={onFile}
      nickChange={onChangeNick}
      nickClick={() => isDuplicatedNick(nickname)}
      msgChange={onChangeMsg}
      value={user.snsEmail}
      nickError={errors.nickname.invalid ? errors.nickname.message : ''}
      msgError={errors.stateMsg.invalid ? errors.stateMsg.message : ''}
    >
    </Profile>
  );
}
