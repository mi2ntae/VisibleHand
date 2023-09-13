import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import styled from 'styled-components';
import http from "api/commonHttp";

export default function MyPageTopLeft() {
    // const userId = useSelector((state) => state.member.memberId);
    const userId = 1;
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
        .then((res) => {
            setProfile((prev) => { return { ...prev, nickname: res.nickname, statusMsg: res.statusMsg, profileImg: res.profileImg}})
        })
        .catch((err) => {
            alert(err)
        })

        http.get(`user/follow/${userId}`)
        .then((res) => {
            setFollowInfo((prev) => { return { ...prev, followingCnt: res.followingCnt, followerCnt: res.followerCnt}})
        })
        .catch((err) => {
            alert(err.message)
        })
    }, [])

    return (
        <TopLeftContainer>
            <Div>
                <ImageContainer>
                    <Image src={profile.profileImg}></Image>
                </ImageContainer>
                <NickName>{profile.nickname}</NickName>
            </Div>
            <FollowInfo>
                <span>팔로잉 : {followInfo.followingCnt}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                <span>팔로워 : {followInfo.followerCnt}</span>
            </FollowInfo>
            <StatusMsg>{profile.statusMsg}</StatusMsg>
        </TopLeftContainer>
    );
}


const TopLeftContainer = styled.div`
    justify-content: center;
    align-items: center;
    box-sizing: border-box;
    // background-color: red;
    width: 30%;
    
`;

const ImageContainer = styled.div`
    width: 110px;
    height: 110px; 
    border: 1px solid lightgray;
    border-radius: 50%;
    overflow: hidden;
    display: inline-block;
`;

const Image = styled.img`
    width: 100%;
    height: 100%;
    object-fit: cover;
`

const Div = styled.div`
    justify-content: center;
    display: flex;
    align-items: center;
    text-align: center;
    margin-top: 10%;
`

const NickName = styled.div`
    display: inline-block;
    margin-left: 18px;
    font-size: 1.8em;
    font-weight: bold;
`

const FollowInfo = styled.div`
    margin-top: 3%;
    font-size: 1.0em;
    font-weight: light;
    width: 100%;
    justify-content: center;
    display: flex;
`
const StatusMsg = styled.div`
    margin-top: 10%;
    font-size: 1.0em;
    font-weight: light;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    width: 100%;
    text-align: center;
`