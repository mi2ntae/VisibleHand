import { black_grey, lightest_grey, white } from "lib/style/colorPalette";
import React from "react";
import styled from "styled-components";

export default function SummaryBubble({ summary }) {
  return <Bubble>{summary}</Bubble>;
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
  max-width: 70%;
  z-index: 999;

  &:before,
  &:after {
    content: "";
    position: absolute;
    right: 2.5rem;
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
