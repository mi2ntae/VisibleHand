import React, { useState } from 'react';
import style from './Profile.css';

function Profile({onSubmit, img, imgClick, imgRef, imgChange, nickChange, nickClick, msgChange, value, nickError, msgError }) {
  const [dropdown, setDropdown] = useState(false);

  const dropdownOpen = () => {
    console.log("click");
    setDropdown(!dropdown);
  };

  const handleMouseLeave = () => {
    setDropdown(false);
  };

    return (
    <div class="form-container">
        <form class="form" onSubmit={onSubmit}>
            <div class="center" >
                 <div class="title">프로필 설정</div>
            </div>
           <br />
            <div class="center">
               <div class="profile-container" onMouseLeave={handleMouseLeave}>
                    <img class="profile" src={img} onClick={() => dropdownOpen()}/>
                    {dropdown &&
                    <div class="dropdown">
                        <button type="button" onClick={imgClick}>이미지 등록</button><br />
                        <button type="button">이미지 삭제</button>
                    </div>}
                    {/* <label class="profileImg-label" htmlFor="profileImg">프로필 이미지 추가</label> */}
                    <input id="profileImg" class="profileImg" type="file" name="file" ref={imgRef} onChange={imgChange}></input><br />
                </div> 
            </div>
            <div>
                닉네임 {nickError && <p class="invalid">{nickError}</p>}
            </div>
            
            <div class="input-container">
               <input class="inputNick" type="text" name="nickname" 
                placeholder='닉네임을 입력하세요.' onChange={nickChange}/>
                <button class="dupbtn" type="button" onClick={nickClick}>중복확인</button>
            </div>
        
            <div>
                상태메시지 {msgError && <p class="invalid">{msgError}</p>}
            </div>
            
            <div>
                <textarea name="stateMsg"  
                placeholder='상태메시지를 입력하세요.' onChange={msgChange}/>
            </div>
            
            
            <div>
                이메일
            </div>
            <div>
                <input class="inputEmail" type="text" name="snsEmail" value={value} readOnly />
            </div>
            <div>
                <button class="subbtn" type="submit">등록</button>
            </div>
        </form>
    </div>
   
   )
}
  
export default Profile;