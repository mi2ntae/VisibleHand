import React from "react";
import styled from "styled-components";
import color from "lib/style/colorPalette";
import { useState } from "react";

export default function Dictionary() {
  const category = ["경영", "경제", "공공", "과학", "금융", "사회"];
  const wordArr = [
    { wordId: 1, category: "경제", word: "혐의거래보고제도" },
    {
      wordId: 2,
      category: "경제",
      word: "현시선호이론 (Revealed Preference Theory)",
    },
    { wordId: 3, category: "경제", word: "협동조합 중간지원기관" },
  ];
  const [currentWordId, setCurrentWordId] = useState(1);
  return (
    <MainContainer>
      <ListContainer>
        <CatContainer>
          <CatItem>전체</CatItem>
          {category.map((v, i) => (
            <CatItem key={i}>{v}</CatItem>
          ))}
          <CatItem>기타</CatItem>
        </CatContainer>
        <SearchContainer>
          <SearchBox placeholder="검색어를 입력하세요"></SearchBox>
          <button>
            <img src={"/icons/feed/ic_search.svg"} alt="검색 버튼" />
          </button>
        </SearchContainer>
        {wordArr.map((v, i) => (
          <WordContainer key={i} onClick={() => setCurrentWordId(v.wordId)}>
            <WordCat>{v.category}</WordCat>
            <div>{v.word}</div>
          </WordContainer>
        ))}
      </ListContainer>
      <DetailContainer>
        <div>
          <WordCat
            style={{
              width: "89px",
              height: "38px",
              fontSize: "26px",
            }}
          >
            {currentWordId}
          </WordCat>
          <div>{currentWordId}</div>
        </div>
        <div>여기는 뜻이 들어갈 자리에요</div>
      </DetailContainer>
    </MainContainer>
  );
}

const MainContainer = styled.div`
  height: 100%;
  display: flex;
  padding: 0px 100px;
  align-items: center;
  border-radius: 16px;
`;

const ListContainer = styled.div`
  display: flex;
  box-sizing: border-box;
  flex-direction: column;
  align-items: center;
  background-color: ${color.white};
  width: 696px;
  height: 848px;
  border-radius: 16px;
  border: 1px solid ${color.lightest_grey};
  margin-right: 36px;
`;

const CatContainer = styled.div`
  margin: 24px 0 12px;
  width: 640px;
  display: flex;
  flex-wrap: nowrap;
  align-items: center;
  justify-content: space-evenly;
`;

const CatItem = styled.button`
  width: 73px;
  height: 36px;
  border: 1px solid ${color.primary};
  background-color: ${color.teritary};
  border-radius: 16px;
  color: ${color.primary};
  font-size: 16px;
  &:focus {
    background-color: ${color.primary};
    color: ${color.white};
  }
`;

const DetailContainer = styled.div`
  box-sizing: border-box;
  padding: 64px 96px 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: ${color.white};
  width: 696px;
  height: 848px;
  border-radius: 16px;
  border: 1px solid ${color.lightest_grey};
  & > div:first-child {
    display: flex;
    align-items: center;
    margin-bottom: 44px;
  }
  & > div:first-child > div:last-child {
    font-size: 48px;
    color: ${color.black_grey};
    font-weight: 600;
    margin-left: 24px;
  }
  & > div:last-child {
    font-size: 20px;
    color: ${color.darkest_grey};
  }
`;

const SearchContainer = styled.div`
  box-sizing: border-box;
  padding: 0 24px;
  margin-bottom: 28px;
  display: flex;
  align-items: center;
  width: 640px;
  height: 52px;
  border: 1px solid ${color.primary};
  border-radius: 16px;
  button {
    border: none;
    background-color: transparent;
  }
`;

const SearchBox = styled.input`
  flex-grow: 5;
  height: 90%;
  border: none;
  outline: none;
  font-size: 16px;
`;

const WordContainer = styled.div`
  display: flex;
  box-sizing: border-box;
  padding: 0 24px;
  margin-bottom: 16px;
  align-items: center;
  width: 640px;
  height: 76px;
  border-radius: 16px;
  background-color: ${color.background};
  box-shadow: 2px 2px 10px ${color.light_grey};
  & > div:last-child {
    color: ${color.black_grey};
    font-size: 20px;
    font-weight: 600;
  }
`;

const WordCat = styled.button`
  margin-right: 16px;
  border: none;
  color: ${color.black_grey};
  font-size: 16px;
  width: 66px;
  height: 28px;
  border-radius: 12px;
  background-color: ${color.teritary};
`;
