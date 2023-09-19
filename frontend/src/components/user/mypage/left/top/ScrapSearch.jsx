import React from "react";
import {
  black_grey,
  lightest_grey,
  primary,
  white,
} from "lib/style/colorPalette.js";
import styled from "styled-components";

export default function search() {
  return (
    <SearchContainer>
      <select style={{border: 0, color: "#2A2B2E", fontSize: "0.8rem", fontWeight: "800", alignItems: 'center'}}>
        <option>기사제목</option>
      </select>
      <div
        style={{ height: "60%", width: "1px", backgroundColor: lightest_grey }}
      />
      <SearchInput placeholder="검색어를 입력하세요." maxLength={11}/>
      <SearchBtn>
        <img src="/icons/feed/ic_search.svg" alt="검색" />
      </SearchBtn>
    </SearchContainer>
  );
}

const SearchContainer = styled.div`
  right: 0%;
  position: absolute;
  display: flex;
  border: 1px solid ${primary};
  border-radius: 16px;
  padding: 0 0.5rem 0 1.25rem;
  background-color: ${white};
  align-items: center;
  width: 50%;
  height: 90%;
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
  width: 100%;
`;

const SearchBtn = styled.button`
  background-color: ${white};
  height: 2.1rem;
  border-radius: 0px 16px 16px 0px;
  border: none;
`;
