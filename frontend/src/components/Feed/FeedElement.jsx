import {
  black_grey,
  dark_grey,
  lightest_grey,
  primary,
  white,
} from "lib/style/colorPalette";
import React from "react";
import { Link } from "react-router-dom";
import { ProfileImg } from "styled";
import styled from "styled-components";

export default function FeedElement({ data }) {
  const handleLike = () => {
    // 좋아요 요청을 보내거라
  };

  const moveToProfile = () => {
    // 해당 프로필로 이동하거라
  };

  return (
    <Feed>
      <div style={{ display: "flex", padding: "32px 32px", gap: "12px" }}>
        <ProfileImg
          src={data.profileImg}
          alt={data.nickname}
          onClick={moveToProfile}
        />
        <div style={{ color: black_grey }}>
          <div style={{ display: "flex", justifyContent: "space-between" }}>
            <div onClick={moveToProfile} style={{ fontWeight: 600 }}>
              {data.nickname}
            </div>
            <Heart onClick={handleLike}>
              {data.isHeart ? (
                <img src="/icons/feed/ic_heart.svg" alt="좋아요" />
              ) : (
                <img src="/icons/feed/ic_heart_empty.svg" alt="좋아요" />
              )}
              {data.heart}
            </Heart>
          </div>
          <Content>{data.content}</Content>
        </div>
      </div>
      <div
        style={{ width: "100%", height: "1px", backgroundColor: lightest_grey }}
      />
      <Article to="/">
        <Label>{data.category}</Label>
        {data.title}
      </Article>
    </Feed>
  );
}

const Feed = styled.div`
  border-radius: 16px;
  border: 1px solid ${lightest_grey};
  background-color: ${white};
`;

const Heart = styled.div`
  display: flex;
  align-items: center;
  gap: 6px;
  color: ${dark_grey};
  font-weight: 500;
`;

const Content = styled.div`
  margin-top: 8px;
  text-align: justify;
  line-height: 24px;
`;

const Article = styled(Link)`
  color: ${black_grey};
  font-weight: 700;
  text-decoration: none;
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 16px 32px;
`;

const Label = styled.div`
  background-color: ${primary};
  width: 88px;
  height: 32px;
  border-radius: 12px;
  color: ${white};
  font-weight: 500;
  display: flex;
  justify-content: center;
  align-items: center;
`;
