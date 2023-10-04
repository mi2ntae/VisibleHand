import React,  {useRef, useEffect, useState} from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { initUser, setUser } from "reducer/userReducer";
import http from "api/commonHttp";
import Profile from 'components/user/login/Profile';
import Swal from "sweetalert2";
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

export default function ProfileUpdate() {
    const user = useSelector((state) => state.user);

    const dispatch = useDispatch();
    const navigate = useNavigate();

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
                provider: user.provider,
            },
            unlinkToken: user.unlinkToken
            }));
        }
    })
    .catch(error => {
        console.log(error);
    });    
    }, [])

    //프로필 사진
    const imgUrl = "https://visiblehand-bucket.s3.ap-northeast-2.amazonaws.com/user_default.png";
    const [file, setFile] = useState("");
    const inputFile = useRef();

    const onFile = (event) => {
      if (event.target.files[0]) {
        setFile(event.target.files[0]);
        // setProfileImg((event.target.files[0]));
      } else {
        setFile("");
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

    const deleteProfileImg = () => {
        setFile("");
        setProfileImg(imgUrl);
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

    //이미지 크기 제한...
    //프로필 수정
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

        let req = null;
        if(profileImg===imgUrl) {
            req = {
                profile: {
                    nickname: event.target.nickname.value,
                    statusMsg: event.target.statusMsg.value,
                },
                profileImg: "default",
                snsEmail: user.snsEmail,
                provider: user.provider
            }
        } else {
            req = {
                profile: {
                    nickname: event.target.nickname.value,
                    statusMsg: event.target.statusMsg.value,
                },
                snsEmail: user.snsEmail,
                provider: user.provider
            };
        }

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
                    provider: user.provider,
                },
                unlinkToken: user.unlinkToken
                }));

                Swal.fire({icon: 'success', title: "프로필이 수정되었습니다."})
                .then(() => {
                    window.location.reload();
                  });
            }
        })
        .catch(error => {
            console.log(error);
        });   
    }

    const unlinkToken = user.unlinkToken;

    //탈퇴
    const deleteUser = () => {
        Swal.fire({
            title: '정말로 탈퇴하실 건가요?',
            text: "다시 되돌릴 수 없습니다. 신중하세요.",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '예',
            cancelButtonText: '아니오',
        }).then((result) => {
            if (result.isConfirmed) {
            Swal.fire({icon: 'success', title: "탈퇴가 완료되었습니다."})

            http
                .delete('/user/'+user.userId)
                .then(response => {
                    console.log(response);

                    if(response.status===200) {
                        if(user.provider==="kakao") {
                            fetch('https://kapi.kakao.com/v1/user/unlink', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/x-www-form-urlencoded', 
                                    'Authorization': 'Bearer ' + unlinkToken
                                },
                            })
                            .then(response => {
                                console.log(response);
                            }).catch(error => {
                                console.error(error);
                            });
                            
                        } else if(user.provider==="google") {
                            fetch('https://accounts.google.com/o/oauth2/revoke?token=' + user.unlinkToken, {
                                method: 'GET',
                            })
                            .then(response => {
                                console.log(response);
                            }).catch(error => {
                                console.error(error);
                            });
                        }

                        dispatch(initUser());
                        navigate('/');
                    }
                })
                .catch(error => {
                    console.log(error);
                });  
            }
        })
    }

    return (
        <div>
            <Profile
            title={"프로필 수정"}
            onSubmit={updateProfile}
            inputImg={() =>  inputFile.current.click()}
            deleteImg={() => deleteProfileImg()}
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
            <ContentWrap>
                 <Button onClick={deleteUser}>탈퇴하기</Button>
            </ContentWrap>
        </div>
    );
}

const ContentWrap = styled.div`
    position: relative;
`;

const Button = styled.button`
    position: absolute;
    right: 0;
    transform: translate(-100%, -200%);
    cursor: pointer;
    font-size: 15px;
    letter-spacing: 2px;
    font-weight: bold;
    padding: 0.7em 2em;
    border: 2px solid #FF0072;
    border-radius: 5px;
    background: white;
    color: #FF0072;
    &:hover {
        background: #FF0072;
        color: white;
        transition: 0.5s;
      }
`