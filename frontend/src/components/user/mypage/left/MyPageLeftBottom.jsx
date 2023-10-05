import React from "react";
import { useSelector } from "react-redux";
import { Background } from "styled";
import styled from "styled-components";
export default function MyPageLeftBottom({ tabs }) {
  const tabNo = useSelector((state) => state.mypageTab.tabNo);

  return (
    <>{tabs.map((tab) => (tabNo === tab.no ? tab.content : <span></span>))}</>
  );
}
