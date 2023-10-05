import { Button } from "@mui/material";
import { grey, light_grey, primary, white } from "lib/style/colorPalette";
import React, { useState } from "react";
import { useEffect } from "react";
import { useNavigate } from "react-router";
import { setUserId } from "reducer/mypageTabReducer";
import { BannerElement, ProfileImg } from "styled";
import { useDispatch, useSelector } from "react-redux";
import http from "api/commonHttp";

export default function User({ user }) {
  const navi = useNavigate();
  const dispatch = useDispatch();
  const [isFollow, setIsFollow] = useState(0);
  const myId = useSelector((state) => state.user.userId);

  const handleFollow = (e) => {
    http
      .post("/user/follow", { fromId: myId, toId: user.userId })
      .then(setIsFollow(1));
    e.stopPropagation();
  };

  const handleUnfollow = (e) => {
    http
      .delete("/user/follow", { data: { fromId: myId, toId: user.userId } })
      .then(setIsFollow(0));
    e.stopPropagation();
  };

  const move = (e) => {
    e.stopPropagation();
    e.preventDefault();
    dispatch(setUserId(user.userId));
    navi("/mypage");
  };

  useEffect(() => console.log(user), []);
  return (
    <BannerElement onClick={(e) => move(e)}>
      <ProfileImg
        src={
          user.profileImg || user.imageUrl
            ? user.profileImg
              ? user.profileImg
              : user.imageUrl
            : "/images/user/user_default.png"
        }
        alt={user.nickname ? user.nickname : user.userName}
      />
      <div style={{ flex: 1 }}>
        <div style={{ fontWeight: 500 }}>
          {user.nickname ? user.nickname : user.userName}
        </div>
        <div
          style={{
            fontSize: "0.875rem",
            color: grey,
            marginTop: "4px",
            whiteSpace: "nowrap",
            overflow: "hidden",
            textOverflow: "ellipsis",
            maxWidth: "235px",
          }}
        >
          {user.statusMsg}
        </div>
      </div>
      {isFollow === 0 ? (
        <Button
          style={{
            backgroundColor: primary,
            color: white,
            fontFamily: "Pretendard",
            borderRadius: "1rem",
            padding: "0.375rem 1rem",
            height: "2.25rem",
          }}
          onClick={handleFollow}
        >
          팔로우
          <img
            src="/icons/feed/ic_account_plus.svg"
            alt="팔로우"
            style={{ marginLeft: "0.375rem" }}
          />
        </Button>
      ) : (
        <Button
          style={{
            backgroundColor: light_grey,
            color: white,
            fontFamily: "Pretendard",
            borderRadius: "1rem",
            padding: "0.375rem 1rem",
            height: "2.25rem",
          }}
          onClick={handleUnfollow}
        >
          언팔로우
          <img
            src="/icons/feed/ic_account_minus.svg"
            alt="팔로우"
            style={{ marginLeft: "0.375rem" }}
          />
        </Button>
      )}
    </BannerElement>
  );
}
