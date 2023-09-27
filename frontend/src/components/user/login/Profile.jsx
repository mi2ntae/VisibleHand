import React, { useRef, useState } from 'react';
import { useSelector } from 'react-redux';
import style from './Profile.css';

function Profile({title, onSubmit, nickChange, nickClick, msgChange, nickError, msgError, nickValue, imgValue, msgValue }) {
  const user = useSelector((state) => state.user);

  //프로필 사진
  const imgUrl = "https://visiblehand-bucket.s3.ap-northeast-2.amazonaws.com/user_default.png";
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

    const [dropdown, setDropdown] = useState(false);

    const handleMouseLeave = () => {
        setDropdown(false);
    };

    return (
    <div class="form-container">
        <form class="form" onSubmit={onSubmit}>
            <div class="center">
                <div class="title">{title}</div>
            </div>
            <div class="center">
               <div class="profile-container" onMouseEnter={() => setDropdown(true)} onMouseLeave={handleMouseLeave}>
                        <img class="profile" src={image} />
                        {dropdown && 
                        <div class="dropdown">
                            <button class="imgbtn" type="button" onClick={() =>  inputFile.current.click()}>이미지 등록</button><br />
                            <button class="imgbtn" type="button" onClick={() => setImage(imgUrl)}>이미지 삭제</button>
                        </div>
                        }
                        <input id="profileImg" class="profileImg" type="file" name="file" value={imgValue} ref={inputFile} onChange={onFile}></input><br />
                    </div>
            </div>
            <div class="center">   
                <div class="name">
                    닉네임 {nickError && <p class="invalid">{nickError}</p>}
                </div>
            
            
                <div class="input-container">
                <input class="inputNick" type="text" name="nickname" value={nickValue}
                    placeholder='닉네임을 입력하세요.' onChange={nickChange}/>
                    <button class="dupbtn" type="button" onClick={nickClick}>중복확인</button>
                </div>
           
                <div class="name">
                    상태메시지 {msgError && <p class="invalid">{msgError}</p>}
                </div>
                <div>
                    <div class="input-container">
                        <textarea name="statusMsg" value={msgValue}
                        placeholder='상태메시지를 입력하세요.' onChange={msgChange}/>
                    </div>
                </div>
                
                <div class="name">
                    이메일
                </div>
                <div>
                    <div class="input-container" >
                        <input class="inputEmail" type="text" name="snsEmail" value={user.snsEmail} readOnly />
                    </div>
                    
                </div>
                <div class="centerbtn">
                    <button class="subbtn" type="submit">등록</button>
                </div>
            </div>
        </form>
    </div>
   
   )
}
  
export default Profile;