import { black_grey, grey, lightest_grey } from "lib/style/colorPalette";
import React from "react";
import { ProfileImg } from "styled";
import Heart from "../Heart";
import styled from "styled-components";

export default function FeedBannerElement({ data, isLast }) {
  return (
    <div style={{ width: "100%" }}>
      <ProfileContainer>
        <ProfileImg
          src={
            data.profileImg ? data.profileImg : "/images/user/user_default.png"
          }
        />
        <div
          style={{
            flex: 1,
            display: "flex",
            flexDirection: "column",
            justifyContent: "center",
            gap: "0.25rem",
          }}
        >
          <div style={{ fontSize: "1rem", color: black_grey, fontWeight: 500 }}>
            {data.nickname}
          </div>
          <div style={{ fontSize: "0.75rem", color: grey, fontWeight: 400 }}>
            {data.createdAt}
          </div>
        </div>
        <Heart clicked={data.isHeart} cnt={data.heart} feedId={data.feedId} />
      </ProfileContainer>
      <div>{data.content}</div>
      {isLast ? (
        ""
      ) : (
        <div
          style={{
            width: "100%",
            height: "1px",
            margin: "1rem 0",
            backgroundColor: lightest_grey,
          }}
        />
      )}
    </div>
  );
}

const ProfileContainer = styled.div`
  display: flex;
  justify-content: space-between;
  gap: 0.5rem;
  margin-bottom: 1rem;
`;
