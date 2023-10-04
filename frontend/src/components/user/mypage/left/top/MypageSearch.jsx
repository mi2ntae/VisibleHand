import React, { useState, useEffect } from "react";
import {
  black_grey,
  lightest_grey,
  primary,
  white,
} from "lib/style/colorPalette.js";
import styled from "styled-components";
import { useDispatch, useSelector } from "react-redux";
import { setKeyword, setSearchType } from "reducer/mypageTabReducer";

export default function ScrapSearch() {
  const [text, setText] = useState("");
  const [type, setType] = useState(0);
  const dispatch = useDispatch();
  const tabNo = useSelector((state) => state.mypageTab.tabNo);

  const options = [
    [{value: "0", content: "기사제목"}, {value: "1", content: "피드내용"}],
    [{value: "0", content: "기사제목"}],
    [{value: "0", content: "퀴즈제목"}]
  ]

  useEffect(() => {
    return () => {
      setText("");
      dispatch(setSearchType(0))
    }
  }, [tabNo])

  const onSearch = () => {
    dispatch(setSearchType(type))
    dispatch(setKeyword(text))
  }

  return (
    <SearchContainer>
      <Select defaultValue="0" onChange={(e) => setType(parseInt(e.target.value))}>
        {options[tabNo].map((opt) => (
          <option value={opt.value}>{opt.content}</option>
        ))}
      </Select>
      <div
        style={{ height: "60%", width: "1px", backgroundColor: lightest_grey }}
      />
      <SearchInput placeholder="검색어를 입력하세요." maxLength={11} value={text} onInput={(e) => setText(e.target.value)}/>
      <SearchBtn onClick={() => onSearch()}>
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

const Select = styled.select`
  border: 0px;
  color: #2A2B2E;
  fontSize: 0.8rem;
  font-weight: 600;
  align-items: center;
`;
