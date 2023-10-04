import {
  black_grey,
  light_grey,
  lightest_grey,
  primary,
  white,
} from "lib/style/colorPalette";
import React from "react";
import styled from "styled-components";

export default function sizeBubble({ setFontSize, fSize, setFontSizeModal }) {
  return (
    <Bubble>
      <Container
        onClick={() => {
          setFontSize("0.75rem");
          setFontSizeModal(false);
        }}
      >
        <Btn value="0.75rem" fSize={fSize}>
          가
        </Btn>
        <SizeDesc value="0.75rem" fSize={fSize}>
          아주작게
        </SizeDesc>
      </Container>
      <Container
        onClick={() => {
          setFontSize("1rem");
          setFontSizeModal(false);
        }}
      >
        <Btn value="1rem" fSize={fSize}>
          가
        </Btn>
        <SizeDesc value="1rem" fSize={fSize}>
          작게
        </SizeDesc>
      </Container>
      <Container
        onClick={() => {
          setFontSize("1.25rem");
          setFontSizeModal(false);
        }}
      >
        <Btn value="1.25rem" fSize={fSize}>
          가
        </Btn>
        <SizeDesc value="1.25rem" fSize={fSize}>
          보통
        </SizeDesc>
      </Container>
      <Container
        onClick={() => {
          setFontSize("1.375rem");
          setFontSizeModal(false);
        }}
      >
        <Btn value="1.375rem" fSize={fSize}>
          가
        </Btn>
        <SizeDesc value="1.375rem" fSize={fSize}>
          크게
        </SizeDesc>
      </Container>
      <Container
        onClick={() => {
          setFontSize("1.5rem");
          setFontSizeModal(false);
        }}
      >
        <Btn value="1.5rem" fSize={fSize}>
          가
        </Btn>
        <SizeDesc value="1.5rem" fSize={fSize}>
          아주크게
        </SizeDesc>
      </Container>
    </Bubble>
  );
}

const Bubble = styled.div`
  position: absolute;
  top: 1rem;
  right: 0rem;
  display: flex;
  background-color: ${white};
  border: 1px solid ${lightest_grey};
  border-radius: 16px;
  padding: 1.5rem;
  margin-top: 20px; // 삼각형과 거리를 주기 위해 조금 더 높게 설정했습니다.
  color: ${black_grey};
  z-index: 999;

  &:before,
  &:after {
    content: "";
    position: absolute;
    right: 4.5rem;
    transform: translateX(-50%);
    width: 0;
    height: 0;
    border-left: 7px solid transparent;
    border-right: 7px solid transparent;
  }

  // 큰 테두리 삼각형
  &:before {
    top: -9px; // 크기에 따라 조정해야 할 수 있습니다.
    border-bottom: 8px solid ${lightest_grey};
  }

  // 작은 배경색 삼각형
  &:after {
    top: -7.6px; // 크기에 따라 조정해야 할 수 있습니다.
    border-bottom: 8px solid ${white};
  }
`;

const Container = styled.button`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
  width: 4rem;
  font-family: Pretendard;
  border: 0;
  background-color: ${white};
  cursor: pointer;
`;

const Btn = styled.div`
  background-color: ${(props) =>
    props.value === props.fSize ? primary : white};
  color: ${(props) => (props.value === props.fSize ? white : black_grey)};
  font-size: ${(props) => props.value};
  border: 1px solid ${light_grey};
  border-radius: 2rem;
  width: 3rem;
  height: 3rem;
  font-weight: 600;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const SizeDesc = styled.span`
  color: ${(props) => (props.value === props.fSize ? primary : black_grey)};
  font-weight: ${(props) => (props.value === props.fSize ? 600 : 400)};
`;
