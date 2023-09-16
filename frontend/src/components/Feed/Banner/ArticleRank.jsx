import http from "api/commonHttp";
import { grey, lightest_grey } from "lib/style/colorPalette";
import React, { useEffect, useState } from "react";
import { Background, BannerElement, BannerTitle } from "styled";
import styled from "styled-components";

export default function ArticleRank() {
  const [articles, setArticles] = useState([]);

  useEffect(() => {
    http
      .get("article/rank")
      .then(({ data }) => {
        setArticles(data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <Background style={{ padding: 32, gap: 14 }}>
      <BannerTitle>
        <span>피드에 많이 언급된 기사</span>
        <span
          style={{
            fontSize: "0.75rem",
            color: grey,
            display: "flex",
            alignItems: "center",
          }}
        >
          <img src="/icons/feed/ic_clock.svg" alt="시계" />
          최근 1시간
        </span>
      </BannerTitle>
      {articles.map((data, index) => {
        return (
          <React.Fragment key={data.articleId}>
            <BannerElement to={`/news/${data.articleId}`}>
              <span style={{ fontSize: "2.25rem", fontWeight: 500 }}>
                {index + 1}
              </span>
              <div style={{ flex: 1 }}>
                <div>{data.title}</div>
                <div style={{ color: grey, fontSize: "0.75rem" }}>
                  {data.company} | {data.createAt}
                </div>
              </div>
              <Thumbnail src={data.thumbnail} alt={data.title} />
            </BannerElement>
            {index !== articles.length - 1 && (
              <div
                style={{
                  width: "100%",
                  height: "1px",
                  backgroundColor: lightest_grey,
                }}
              />
            )}
          </React.Fragment>
        );
      })}
    </Background>
  );
}

const Thumbnail = styled.img`
  width: 5.5rem;
  height: 3.5rem;
  border-radius: 0.5rem;
`;
