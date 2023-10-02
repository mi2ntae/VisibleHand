import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import style from './Profile.css';

function Profile({title, onSubmit, inputImg, deleteImg, imgRef, imgChange, nickChange, nickClick, msgChange, nickError, msgError, nickValue, imgValue, msgValue }) {
  const user = useSelector((state) => state.user);

    const imgUrl = "https://visiblehand-bucket.s3.ap-northeast-2.amazonaws.com/user_default.png";

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
                        <img class="profile" src={imgValue!=null && imgValue!="" ? imgValue : imgUrl} />
                        <img class="dropdownbtn" src="https://cdn-icons-png.flaticon.com/512/7601/7601867.png"></img>
                        {dropdown && 
                        <div class="dropdown">
                            <button class="imgbtn" type="button" onClick={inputImg}>이미지 등록</button><br />
                            <button class="imgbtn" type="button" onClick={deleteImg}>이미지 삭제</button>
                        </div>
                        }
                        <input id="profileImg" class="profileImg" type="file" name="file" ref={imgRef} onChange={imgChange}></input><br />
                    </div>
            </div>
            <div class="center">   
                <div class="name">
                    닉네임 {nickError && <p class="invalid">{nickError}</p>}
                </div>
            
            
                <div class="input-container">
                <input class="inputNick" type="text" name="nickname" defaultValue={nickValue!="" ? nickValue : ""} 
                    placeholder='닉네임을 입력하세요.' onChange={nickChange}/>
                    <button class="dupbtn" type="button" onClick={nickClick}>중복확인</button>
                </div>
           
                <div class="name">
                    상태메시지 {msgError && <p class="invalid">{msgError}</p>}
                </div>
                <div>
                    <div class="input-container">
                        <textarea name="statusMsg" defaultValue={msgValue!="" ? msgValue : ""}
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