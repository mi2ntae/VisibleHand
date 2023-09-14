import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import http from "api/commonHttp";

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
        <RightTopContainer>
            <Div>
                <ImageContainer>
                    <Image src={profile.profileImg}></Image>
                </ImageContainer>
                <div>
                    <NickName>{profile.nickname}</NickName>
                    <FollowInfo>
                        <span>팔로잉 : {followInfo.followingCnt}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                        <span>팔로워 : {followInfo.followerCnt}</span>
                    </FollowInfo>
                </div>
                <Button>asd</Button>
                
            </Div>
           
            <StatusMsg>{profile.statusMsg}</StatusMsg>
        </RightTopContainer>
    );
}


const RightTopContainer = styled.div`
    justify-content: center;
    align-items: center;
    box-sizing: border-box;
    background-color: white;
    border-radius: 20px;
    box-shadow: 3px 3px 3px lightgrey;
    width: 100%;
    padding-top: 1px;
    padding-bottom: 20px;
`;

const ImageContainer = styled.div`
    width: 100px;
    height: 100px; 
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
    display: flex;
    align-items: center;
    text-align: left;
    margin-top: 5%;
    margin-left: 5%;
`

const NickName = styled.div`
    display: inline-block;
    font-size: 1.5em;
    font-weight: bold;
    margin-left: 10%;
`

const FollowInfo = styled.div`
    font-size: 0.8em;
    font-weight: light;
    min-width: 120px;
    margin-top: 5%;
    margin-left: 10%;
`
const StatusMsg = styled.div`
    margin-top: 3%;
    font-size: 0.9em;
    font-weight: light;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    width: 100%;
    text-align: center;
`

const Button = styled.button`
    margin-right: 5%;
    margin-bottom: 25%;
    margin-left: 15%;
    font-size: 1.0em;
    font-weight: light;
    text-align: center;
`