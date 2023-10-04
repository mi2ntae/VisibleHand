import React from 'react';
import LoginBtn from "components/user/login/LoginBtn";
import styled from "styled-components";
import Discription from 'components/user/login/Discription';

export default function Login() {
    const GOOGLE_CLIENT_ID=process.env.REACT_APP_GOOGLE_CLIENT_ID;
    const GOOGLE_REDIRECT_URI=process.env.REACT_APP_GOOGLE_REDIRECT_URI;

    const KAKAO_CLIENT_ID = process.env.REACT_APP_KAKAO_CLIENT_ID;
    const KAKAO_REDIRECT_URI = process.env.REACT_APP_KAKAO_REDIRECT_URI;

    const googleLink = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${GOOGLE_CLIENT_ID}&redirect_uri=${GOOGLE_REDIRECT_URI}&response_type=code&scope=email`;
    const kakaoLink = `https://kauth.kakao.com/oauth/authorize?client_id=${KAKAO_CLIENT_ID}&redirect_uri=${KAKAO_REDIRECT_URI}&response_type=code`;

    const googleLogin = () => {
        window.location.href = googleLink;

    };
    const kakaoLogin = () => {
        window.location.href = kakaoLink;
    };  

    return (
        <ContentWrap>
            <Container>
                <Discription>
                </Discription>                
                <LoginBtn background={"white"} title={"구글로 시작하기"} onClick={googleLogin}
                    imgWidth={25} imgHeight={25} imgSrc={"https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Google_%22G%22_Logo.svg/2048px-Google_%22G%22_Logo.svg.png"} imgAlt={"Google Logo"}
                    imgPadding={10}
                ></LoginBtn>
                <br></br>
                <LoginBtn background={"#F7E600"} title={"카카오로 시작하기"} onClick={kakaoLogin}
                    imgWidth={45} imgHeight={45} imgSrc={"https://cs.kakao.com/img/cskakaocom/pc/thumb/thumb_kakaotalk.png"} imgAlt={"Kakao Logo"}
                ></LoginBtn>
             </Container>
         </ContentWrap>
    );
}

const ContentWrap = styled.div`
    position: relative;
    height: 100%;
    width: 100%;
`;
const Container = styled.div`
    position: absolute;
    top: 30%;
    left: 23%;
`;

