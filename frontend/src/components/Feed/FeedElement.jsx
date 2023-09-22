import {
  black_grey,
  grey,
  lightest_grey,
  primary,
  white,
} from "lib/style/colorPalette";
import React, { useEffect, useMemo, useRef, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { ProfileImg } from "styled";
import styled from "styled-components";
import Heart from "components/Feed/Heart";

export default function FeedElement({ data }) {
  const navigate = useNavigate();
  const moveToProfile = () => {
    navigate(`/profile/${data.nickname}`);
  };

  const [moreBtn, setMoreBtn] = useState(false);
  const textLimit = useRef(170);
  const text = useMemo(() => {
    const short = data.content.slice(0, textLimit.current);
    if (data.content.length > textLimit.current) {
      if (moreBtn) {
        return data.content;
      }
      return short;
    }
    return data.content;
  }, [moreBtn]);

  useEffect(() => console.log(data));

  return (
    <Feed>
      <div style={{ display: "flex", padding: "32px 32px", gap: "12px" }}>
        <ProfileImg
          src={data.profileImg}
          alt={data.nickname}
          onClick={moveToProfile}
          style={{ cursor: "pointer" }}
        />
        <div style={{ color: black_grey, flex: 1 }}>
          <div style={{ display: "flex", justifyContent: "space-between" }}>
            <div
              onClick={moveToProfile}
              style={{ fontWeight: 600, cursor: "pointer" }}
            >
              {data.nickname}
            </div>
            <Heart
              clicked={data.isHeart}
              cnt={data.heart}
              feedId={data.feedId}
            />
          </div>
          <Content>
            {text}
            <More onClick={() => setMoreBtn(!moreBtn)}>
              {data.content.length > textLimit.current &&
                (moreBtn ? " [닫기]" : " ...더보기")}
            </More>
          </Content>
        </div>
      </div>
      <div
        style={{ width: "100%", height: "1px", backgroundColor: lightest_grey }}
      />
      <Article to={`/news/${data.articleId}`}>
        <Label>{data.category}</Label>
        {data.title}
      </Article>
    </Feed>
  );
}

const Feed = styled.div`
  border-radius: 16px;
  border: 1px solid ${lightest_grey};
  background-color: ${white};
`;

const Content = styled.div`
  margin-top: 8px;
  text-align: justify;
  line-height: 24px;
`;

const More = styled.span`
  color: ${grey};
  cursor: pointer;
`;

const Article = styled(Link)`
  color: ${black_grey};
  font-weight: 700;
  text-decoration: none;
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 16px 32px;
`;

const Label = styled.div`
  background-color: ${primary};
  width: 88px;
  height: 32px;
  border-radius: 12px;
  color: ${white};
  font-weight: 500;
  display: flex;
  justify-content: center;
  align-items: center;
`;
