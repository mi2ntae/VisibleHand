import React,  {useRef, useEffect, useState} from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { setUser } from "reducer/userReducer";
import http from "api/commonHttp";
import { useNavigate } from 'react-router-dom';
import Profile from 'components/user/login/Profile';
import Swal from "sweetalert2";

export default function ProfileUpdate() {
    const user = useSelector((state) => state.user);

    const navigate = useNavigate();
    const dispatch = useDispatch();

    //프로필 가져오기
    const [nickname, setNickname] = useState('');
    const [statusMsg, setStatusMsg] = useState('');
    const [profileImg, setProfileImg] = useState('');

    useEffect(() => {
        http.get('/user/profile/'+user.userId,
    )
    .then(response => {
        if(response.status===200) {
            setNickname(response.data.nickname);
            setStatusMsg(response.data.statusMsg);
            setProfileImg(response.data.profileImg);

            dispatch(setUser({
            token: {
                accessToken: user.accessToken,
                refreshToken: user.refreshToken,
            },
            user: {
                userId: user.userId,
                nickname: response.data.nickname,
                snsEmail: user.snsEmail,
                provider: user.provider
            }
            }));
        }
    })
    .catch(error => {
        console.log(error);
    });    
    }, [])

    //프로필 사진
    const imgUrl = "https://visiblehand-bucket.s3.ap-northeast-2.amazonaws.com/user_default.png";
    // const [image, setImage] = useState(imgUrl); // 프로필 이미지 초기값 설정
    const [file, setFile] = useState("");
    const inputFile = useRef();

    const onFile = (event) => {
      if (event.target.files[0]) {
        setFile(event.target.files[0]);
        setProfileImg((event.target.files[0]));
      } else {
        setProfileImg(imgUrl);
        return;
      }

      const reader = new FileReader();
        reader.onload = () => {
            if(reader.readyState===2) {
                setProfileImg(reader.result);
            }
        }

        reader.readAsDataURL(event.target.files[0]);
    }

    //닉네임, 상태메시지 유효성
    const onChangeNick = (event) => {
    const newNickname = event.target.value;
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
    const newStatusMsg = event.target.value;
    setStatusMsg(newStatusMsg);

    if(newStatusMsg.length>128) {
    setErrors((prev) => ({
        ...prev,
        statusMsg: {
        ...prev.statusMsg,
        invalid: true,
        },
    }));
    } else {
    setErrors((prev) => ({
        ...prev,
        statusMsg: {
        ...prev.statusMsg,
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
    statusMsg: {
        invalid: false,
        message: "상태메시지는 128자 이하여야 합니다.",
    }
    })

    //닉네임 중복 체크
    const [dupCheck, setDupCheck] = useState(false);

    const isDuplicatedNick = (nickname) => {
    if(nickname==null || nickname==="") {
        Swal.fire({icon: 'warning', title: "닉네임을 입력해주세요."});
    return;
    }

    http.get('/user/auth?nickname='+nickname)
    .then(({ data }) => {
    if(data.isDuplicated===0) {
        setDupCheck(true);
        Swal.fire({icon: 'success', title: "사용가능한 닉네임입니다."});
        setNickname(nickname);

    } else if(data.isDuplicated===1) {
        if(user.nickname===nickname) {
            setDupCheck(true);
            Swal.fire({icon: 'warning', title: "현재 닉네임과 동일합니다."});
            setNickname(nickname);
        } else {
            setDupCheck(false);
            Swal.fire({icon: 'error', title: "사용할 수 없는 닉네임입니다. 다른 닉네임을 입력해주세요."})
            setNickname(nickname);
      }
    }
    })
    .catch(error => {
    console.log(error);
    });  
    }
    //프로필 수정
    //이미지 삭제랑
    //이미지 변경하지 않는 거랑 구분되도록
    const updateProfile = (event) => {
        event.preventDefault();
        
        if(user.nickname===nickname) {
            setDupCheck(true);
        }
        else if(!dupCheck) {
            alert("닉네임 중복 확인을 완료해주세요.");
            return;
        }

        const fileInput = event.target.querySelector('input[type="file"]');
        const formData = new FormData();

        const req = {
            profile: {
                nickname: event.target.nickname.value,
                statusMsg: event.target.statusMsg.value,
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

        http.put('/user/profile/'+user.userId,
            formData,
            { headers: { 'content-type': `multipart/form-data` }
        })
        .then(response => {
            if(response.status===200) {
                dispatch(setUser({
                token: {
                    accessToken: user.accessToken,
                    refreshToken: user.accessToken,
                },
                user: {
                    userId: user.userId,
                    nickname: req.profile.nickname,
                    snsEmail: user.snsEmail,
                    provider: user.provider
                }
                }));

                Swal.fire({icon: 'success', title: "프로필이 수정되었습니다."});
            }
        })
        .catch(error => {
            console.log(error);
        });   
    }

    //탈퇴
    //카카오에서도 연결 끊기
    const deleteUser = () => {
        //모달 띄워서 진짜 탈퇴할 거냐고 묻기

        http
        .delete('/user/'+user.userId)
        .then(response => {
            console.log(response);
        })
        .catch(error => {
            console.log(error);
        });  
    }

    return (
        <div>

            <Profile
            title={"프로필 수정"}
            onSubmit={updateProfile}
            inputImg={() =>  inputFile.current.click()}
            deleteImg={() => setProfileImg(imgUrl)}
            imgRef={inputFile}
            imgChange={onFile}
            nickChange={onChangeNick}
            nickClick={() => isDuplicatedNick(nickname)}
            msgChange={onChangeMsg}
            nickError={errors.nickname.invalid ? errors.nickname.message : ''}
            msgError={errors.statusMsg.invalid ? errors.statusMsg.message : ''}
            nickValue={nickname}
            imgValue={profileImg}
            msgValue={statusMsg}
            >
            </Profile>
            <div>
                 <button onClick={deleteUser}>탈퇴하기</button>
            </div>
            <div>
                 <button onClick={() => navigate('/')}>메인으로 이동</button>
            </div>
           
        </div>
    );
}