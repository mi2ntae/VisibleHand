import http from "api/commonHttp";
import {
  black_grey,
  grey,
  light_grey,
  lightest_grey,
  primary,
  secondary,
  white,
} from "lib/style/colorPalette";
import React, { useEffect, useState } from "react";
import { Background, ProfileImg } from "styled";
import styled from "styled-components";
import Swal from "sweetalert2";

export default function FeedInput({ articleId, userId, setFeeds }) {
  const [content, setContent] = useState("");
  const [isShared, setIsShared] = useState(true);
  const [feedId, setFeedId] = useState(0);
  const [profileImage, setProfileImage] = useState("");

  function getFeeds() {
    http
      .get(`/feed/list/${articleId}/${userId}?page=0`)
      .then((data) => setFeeds(data.data))
      .catch((err) => alert(err));
  }

  useEffect(() => {
    http.get(`/feed/${articleId}/${userId}`).then((data) => {
      setFeedId(data.data.feedId);
      if (data.data.content !== null) {
        setContent(data.data.content);
      }
    });

    http
      .get(`/user/profile/${userId}`)
      .then((data) => setProfileImage(data.data.profileImg));
  }, []);

  const handleSubmit = (e) => {
    if (feedId < 0) {
      http
        .post(`/feed`, {
          articleId: articleId,
          content: content,
          shared: isShared,
          userId: userId,
        })
        .then((data) => {
          setFeedId(data.data);
          Swal.fire({
            title: "등록이 완료되었습니다",
            imageUrl: "/icons/quiz/ic_right.svg",
            width: 600,
            showConfirmButton: false,
            timer: 1000,
          }).then(() => {
            getFeeds();
          });
        })
        .catch((err) => console.log(err));
    } else {
      http
        .put(`/feed/${feedId}`, { content: content, shared: isShared })
        .then(() => {
          Swal.fire({
            title: "등록이 완료되었습니다",
            imageUrl: "/icons/quiz/ic_right.svg",
            width: 600,
            showConfirmButton: false,
            timer: 1000,
          }).then(() => {
            getFeeds();
          });
        })
        .catch((err) => {
          alert(err);
        });
    }
  };
  return (
    <Background style={{ gap: "1rem", height: "300px" }}>
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          gap: "1rem",
        }}
      >
        <ProfileImg
          src={profileImage ? profileImage : "/images/user/user_default.png"}
        />
        <div style={{ flex: 1 }}>
          <Switch>
            <SwitchInput
              checked={isShared}
              onChange={() => setIsShared((prev) => !prev)}
            />
            <Slider />
            {isShared ? (
              <LabelText left="0.5em">공개</LabelText>
            ) : (
              <LabelText right="0.5em">비공개</LabelText>
            )}
          </Switch>
        </div>
        <SubmitBtn content={content} onClick={handleSubmit}>
          {feedId === -1 ? "등록" : "수정"}
        </SubmitBtn>
      </div>
      <Input
        type="text"
        value={content}
        onChange={(e) => setContent(e.target.value)}
        placeholder="내용을 입력해주세요."
        maxLength={500}
      />
    </Background>
  );
}

const SubmitBtn = styled.button`
  background-color: ${white};
  border: 0;
  color: ${(props) => (props.content.length > 0 ? primary : grey)};
  font-size: 1rem;
  font-family: Pretendard;
  cursor: ${(props) => (props.content.length > 0 ? "pointer" : "")};
`;

const Input = styled.textarea`
  border: 0;
  font-family: Pretendard;
  font-size: 1rem;
  flex: 1;
  resize: none;
  outline: none;
  color: ${black_grey};
  &::placeholder {
    color: ${lightest_grey};
  }
`;

const Switch = styled.label`
  font-size: 17px;
  position: relative;
  display: inline-block;
  width: 5em;
  height: 1.5em;
`;

const Slider = styled.span`
  position: absolute;
  cursor: pointer;
  inset: 0;
  background: ${light_grey};
  border-radius: 50px;
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);

  &:before {
    position: absolute;
    content: "";
    display: flex;
    align-items: center;
    justify-content: center;
    height: 1.5em;
    width: 1.5em;
    inset: 0;
    background-color: white;
    border-radius: 50px;
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.4);
    transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  }
`;

const SwitchInput = styled.input.attrs({ type: "checkbox" })`
  opacity: 0;
  width: 0;
  height: 0;

  &:checked + ${Slider} {
    background: ${secondary};
  }

  &:focus + ${Slider} {
    box-shadow: 0 0 1px #0974f1;
  }

  &:checked + ${Slider}:before {
    transform: translateX(3.6em);
  }
`;

const LabelText = styled.span`
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  right: ${(props) => props.right || "initial"};
  left: ${(props) => props.left || "initial"};
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  color: ${white};
`;
