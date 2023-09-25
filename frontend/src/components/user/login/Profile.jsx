import React from 'react';
import { useSelector} from 'react-redux';

function Profile({onSubmit, onChange, onClick, value}) {
   return (
    <form onSubmit={onSubmit}>
        <strong>프로필 설정</strong><br />
        프로필 사진 <input type="file" name="file"></input><br />
        닉네임 <input type="text" name="nickname" placeholder='닉네임을 입력하세요.' onChange={onChange}/>
        <button type="button" onClick={onClick}>중복확인</button><br />
        상태메시지 <input type="text" name="stateMsg" placeholder='상태메시지를 입력하세요.' /><br />
        이메일 <input type="text" name="snsEmail" value={value} readOnly /><br />
        <button type="submit">등록</button>
    </form>
   )
}
  
export default Profile;