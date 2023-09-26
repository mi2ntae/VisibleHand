import React, { useState } from "react";
import {
  black_grey,
  lightest_grey,
  primary,
  white,
} from "lib/style/colorPalette.js";
import styled from "styled-components";

export default function Search({
  setSearchType,
  setKeyWord,
  btnClick,
  setBtnClick,
}) {
  const [type, setType] = useState(true);
  const [content, setContent] = useState("");

  const handleButton = () => {
    setKeyWord(content);
    setSearchType(type);
    setBtnClick(!btnClick);
  };

  const handleKeyPress = (e) => {
    if (e.key === "Enter") {
      handleButton();
    }
  };
  return (
    <SearchContainer>
      <select
        style={{
          border: 0,
          fontSize: "1rem",
          width: "10rem",
          outline: "none",
          marginRight: "1rem",
        }}
        onChange={(e) => setType(e.target.value === "true")}
        value={type}
      >
        <option value={true}>피드내용</option>
        <option value={false}>닉네임</option>
      </select>
      <div
        style={{ height: "60%", width: "1px", backgroundColor: lightest_grey }}
      />
      <SearchInput
        placeholder="검색어를 입력하세요."
        value={content}
        onChange={(e) => {
          setContent(e.target.value);
        }}
      />
      <SearchBtn onClick={handleButton} onKeyDown={handleKeyPress}>
        <img src="/icons/feed/ic_search.svg" alt="검색" />
      </SearchBtn>
    </SearchContainer>
  );
}

const SearchContainer = styled.div`
  display: flex;
  border: 1px solid ${primary};
  border-radius: 16px;
  padding: 0 1.125rem 0 1.25rem;
  background-color: ${white};
  align-items: center;
`;

const SearchInput = styled.input`
  flex: 1;
  //   height: 52px;
  font-family: "Pretendard";
  border-radius: 16px 0px 0px 16px;
  border: none;
  outline: none;
  font-size: 1rem;
  color: ${black_grey};
  margin-left: 1.5rem;
  &::placeholder {
    color: ${lightest_grey};
  }
`;

const SearchBtn = styled.button`
  background-color: ${white};
  height: 3.25rem;
  border-radius: 0px 16px 16px 0px;
  border: none;
  outline: none;
`;
