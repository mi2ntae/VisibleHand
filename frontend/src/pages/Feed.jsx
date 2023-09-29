import React, { useEffect, useRef, useState } from "react";
import styled from "styled-components";
import Search from "components/Feed/Search";
import FeedElement from "components/Feed/FeedElement";
import ArticleRank from "components/Feed/Banner/ArticleRank";
import UserRecommend from "components/Feed/Banner/UserRecommend";
import http from "api/commonHttp";
import UserElement from "components/Feed/UserElement";
import { background } from "lib/style/colorPalette";
import { useSelector } from "react-redux";

export default function Feed() {
  const userId = useSelector((state) => state.user.userId);
  const [feeds, setFeeds] = useState([]);
  const [users, setUsers] = useState([]);
  const [searchType, setSearchType] = useState(true);
  const [keyword, setKeyWord] = useState("");
  const page = useRef(0);
  const [loading, setLoading] = useState(false);
  const target = useRef(null);
  const [btnClick, setBtnClick] = useState(false);
  const [showObserver, setShowObserver] = useState(true);
  const [recommendUsers, setRecommendUsers] = useState([]);
  useEffect(() => {
    console.log("Keyword changed:", keyword);
  }, [keyword]);

  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      if (!entry.isIntersecting) return;
      if (loading) return;
      console.log(keyword);
      setLoading(true);
      http
        .get(
          `feed/list/${userId}?searchType=${
            searchType ? 0 : 1
          }&keyword=${keyword}&page=${page.current}`
        )
        .then((data) => {
          console.log(data);
          searchType
            ? setFeeds((prevFeeds) => [...prevFeeds, ...data.data])
            : setUsers((prevUsers) => [...prevUsers, ...data.data]);
          console.log(data.data);
        })
        .catch((err) => {
          alert(err);
        });
      console.log("fetchData");
      setLoading(false);
      page.current += 1;
    });
  });

  useEffect(() => {
    observer.observe(target.current);
    http
      .get(`user/recommend/${userId}`)
      .then((data) => setRecommendUsers(data.data))
      .catch((err) => console.log(err));
  }, []);

  useEffect(() => {
    setLoading(true);
    setShowObserver(true);
    page.current = 0;
    http
      .get(
        `feed/list/${userId}?searchType=${
          searchType ? 0 : 1
        }&keyword=${keyword}&page=${page.current}`
      )
      .then((data) => {
        searchType ? setFeeds(data.data) : setUsers(data.data);
        if (searchType && data.data.length < 6) setShowObserver(false);
        if (!searchType && data.data.length < 8) setShowObserver(false);
      })
      .catch((err) => alert(err));
    console.log("resetData");
    setLoading(false);
  }, [btnClick]);

  return (
    <div style={{ display: "flex", padding: "40px 100px", gap: "88px" }}>
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          flex: 13,
          gap: "16px",
          overFlowY: "auto",
        }}
      >
        <Search
          setSearchType={setSearchType}
          setKeyWord={setKeyWord}
          btnClick={btnClick}
          setBtnClick={setBtnClick}
        />
        <FeedContainer>
          {/* {searchType ? "피드" : "닉네임"} */}
          {searchType
            ? feeds.map((data) => {
                return <FeedElement data={data} key={data.feedId} />;
              })
            : users.map((data) => {
                return <UserElement user={data} key={data.userId} />;
              })}
          {showObserver ? (
            <div
              id="scrollEnd"
              style={{ height: "1px", backgroundColor: background }}
              ref={target}
            ></div>
          ) : (
            ""
          )}
        </FeedContainer>
      </div>
      <BannerContainer>
        <ArticleRank />
        <UserRecommend users={recommendUsers} />
      </BannerContainer>
    </div>
  );
}

const FeedContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 24px;
`;

const BannerContainer = styled.div`
  flex: 7;
  display: flex;
  flex-direction: column;
  gap: 24px;
  overflow-y: auto;
  position: sticky;
  align-self: flex-start;
  top: 40px;
`;
