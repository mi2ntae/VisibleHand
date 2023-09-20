import React from 'react';

export default function Login() {
    const GOOGLE_CLIENT_ID=process.env.REACT_APP_GOOGLE_CLIENT_ID;
    const GOOGLE_REDIRECT_URI=process.env.REACT_APP_GOOGLE_REDIRECT_URI;

    const KAKAO_CLIENT_ID = process.env.REACT_APP_KAKAO_CLIENT_ID;
    const KAKAO_REDIRECT_URI = process.env.REACT_APP_KAKAO_REDIRECT_URI;

    const googleLink = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${GOOGLE_CLIENT_ID}&redirect_uri=${GOOGLE_REDIRECT_URI}&response_type=code&scope=email`
    const kakaoLink = `https://kauth.kakao.com/oauth/authorize?client_id=${KAKAO_CLIENT_ID }&redirect_uri=${KAKAO_REDIRECT_URI}&response_type=code`;

    const googleLogin = () => {
        window.location.href = googleLink;

    };
    const kakaoLogin = () => {
        window.location.href = kakaoLink;
    };  


    return (
        <div>
            <button type='button' onClick={googleLogin}>
                구글 로그인
            </button>
            <button type='button' onClick={kakaoLogin}>
                카카오 로그인
            </button>
        </div>
        
    );
}