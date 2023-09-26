import { Button } from "@mui/material";
import { grey, lightest_grey, primary, white } from "lib/style/colorPalette";
import React from "react";
import { ProfileImg } from "styled";
import styled from "styled-components";

export default function UserElement({ user }) {
  const handleFollow = (e) => {
    e.preventDefault();
  };
  return (
    <UserContainer>
      <ProfileImg src={user.profileImg ? user.profileImg : user.imageUrl} />
      <div style={{ flex: 1 }}>
        <div style={{ fontWeight: 500 }}>
          {user.nickname ? user.nickname : user.userName}
        </div>
        <div style={{ fontSize: "0.875rem", color: grey }}>
          {user.statusMsg}
        </div>
      </div>
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
    </UserContainer>
  );
}

const UserContainer = styled.div`
  border-radius: 1rem;
  background-color: ${white};
  border: 1px solid ${lightest_grey};
  padding: 2rem;
  flex: 1;
  display: flex;
  gap: 0.75rem;
  align-items: center;
`;
