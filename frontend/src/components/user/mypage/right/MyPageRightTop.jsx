import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import http from "api/commonHttp";
import { Background } from 'styled';
import { black_grey, dark_grey, darkest_grey, white } from 'lib/style/colorPalette';

export default function MyPageRightTop({userId}) {
    const [profile, setProfile] = useState({
        nickname: "",
        statusMsg: "",
        profileImg: ""
    });
    const [followInfo, setFollowInfo] = useState({
        followingCnt: 0,
        followerCnt: 0
    });
    useEffect(() => {
        http.get(`user/profile/${userId}`)
        .then(({data}) => {
            setProfile((prev) => { return { ...prev, nickname: data.nickname, statusMsg: data.statusMsg, profileImg: data.profileImg}})
        })
        .catch((err) => {
            alert(err)
        })

        http.get(`user/follow/${userId}`)
        .then(({data}) => {
            setFollowInfo((prev) => { return { ...prev, followingCnt: data.followingCnt, followerCnt: data.followerCnt}})
        })
        .catch((err) => {
            alert(err.message)
        })
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])

    return (
        <Background style={{ padding: "3rem", gap: "1.25rem" }}>
            <Div>
                <Image src={profile.profileImg}></Image>
                <div style={{display: "flex", flexDirection: "column", justifyContent: "flex-start", flex: 1}}>
                    <div style={{display: "flex", justifyContent: "flex-end"}}>
                        <Button><img src='/icons/mypage/ic_settings.svg' alt='회원정보 수정'/></Button>
                    </div>
                    
                    <NickName>{profile.nickname}</NickName>
                    <FollowInfo>
                        <span>팔로잉</span><span style={{fontWeight: 500}}> {followInfo.followingCnt}</span>&nbsp;&nbsp;
                        <span>팔로워</span><span style={{fontWeight: 500}}> {followInfo.followerCnt}</span>
                    </FollowInfo>
                </div>
            </Div>
            <StatusMsg>{profile.statusMsg}</StatusMsg>
        </Background>
    );
}


const Image = styled.img`
    width: 6rem;
    height: 6rem;
    border: 1px solid ${dark_grey};
    border-radius: 3.75rem;
    object-fit: cover;
`

const Div = styled.div`
    display: flex;
    align-items: center;
    text-align: left;
    gap: 1.75rem;
`

const NickName = styled.div`
    font-size: 1.5rem;
    font-weight: 600;
    color: ${black_grey};
`

const FollowInfo = styled.div`
    font-size: 1rem;
    font-weight: 400;
    color: ${black_grey};
    margin-top: 1.125rem;
`
const StatusMsg = styled.div`
    color: ${darkest_grey};
    font-size: 1rem;
`

const Button = styled.button`
    border: none;
    background-color: ${white};
    width: 1.125rem;
    height: 1.125rem;
`