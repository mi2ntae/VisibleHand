import React, { useEffect, useState } from "react";
import SizeBubble from "components/news/SizeBubble";
import SummaryBubble from "components/news/SummaryBubble";
import {
  grey,
  lightest_grey,
  primary,
  white,
  white_grey,
} from "lib/style/colorPalette";
import http from "api/commonHttp";
import styled from "styled-components";
import { useSelector } from "react-redux";
import Swal from "sweetalert2";

export default function NewsContent({ articleId }) {
  const [article, setArticle] = useState([]);
  const [scraped, setScraped] = useState(true);
  const [quiz, setQuiz] = useState({});
  const [text, setText] = useState("");
  const userId = useSelector((state) => state.user.userId);
  useEffect(() => {
    http
      .get(`/article/${articleId}/${userId}`)
      .then((data) => {
        setArticle(data.data.article);
        setScraped(data.data.scraped);
      })
      .catch((error) => alert(error));

    http
      .get(`quiz/article/${articleId}`)
      .then((res) => {
        if (res.data.newsQuizId === -1) return;
        setQuiz(res.data);
      })
      .catch((err) => alert(err));
  }, []);

  // 글자크기 모달
  const [fSize, setFSize] = useState("1rem");
  const [fontSizeModal, setFontSizeModal] = useState(false);

  // 기사요약 모달
  const [summaryModal, setSummaryModal] = useState(false);

  // 북마크 로직
  const handleBookmark = () => {
    if (scraped) {
      http
        .delete(`article/scrap/${articleId}/${userId}`)
        .then(() => setScraped(false));
    } else {
      http.post(`article/scrap/${articleId}/${userId}`).then(() => {
        setScraped(true);
      });
    }
  };

  const transformLazyLoad = (content) => {
    const dom = new DOMParser().parseFromString(content, "text/html");

    dom.querySelectorAll("img[data-src]").forEach((img) => {
      img.setAttribute("src", img.getAttribute("data-src"));
      img.removeAttribute("data-src");

      const wrapper = dom.createElement("div");
      wrapper.style.position = "relative";
      wrapper.style.width = "100%";
      wrapper.style.paddingBottom = "60%";

      img.style.position = "absolute";
      img.style.width = "100%";
      img.style.height = "100%";
      img.style.objectFit = "contain";
      img.style.top = "0";
      img.style.left = "0";

      img.parentNode.insertBefore(wrapper, img);
      wrapper.appendChild(img);
    });

    dom.querySelectorAll("em").forEach((em) => {
      em.style.display = "block";
      em.style.textAlign = "center";
      em.style.color = grey;
      em.style.fontStyle = "normal";
      em.style.margin = "0.5rem auto";
    });

    return dom.body.innerHTML;
  };

  const SafeHtml = ({ content }) => {
    const safeContent = transformLazyLoad(content);

    return (
      <div
        dangerouslySetInnerHTML={{ __html: safeContent }}
        style={{ fontSize: fSize }}
      />
    );
  };

  //퀴즈 채점
  const mark = () => {
    if (text === quiz.answer) {
      //정답
      http
        .put("quiz", {
          userId: userId,
          newsquizId: quiz.newsQuizId,
          correct: true,
        })
        .then(() => {
          Swal.fire({
            title: "정답입니다!",
            imageUrl: "/icons/quiz/ic_right.svg",
            width: 600,
            showConfirmButton: false,
            showDenyButton: false,
            timer: 1000,
          }).then(() => {});
        })
        .catch((err) => {
          alert(err);
        });
    } else {
      //오답
      http
        .put("quiz", {
          userId: userId,
          newsquizId: quiz.newsQuizId,
          correct: false,
        })
        .then(() => {
          Swal.fire({
            title: "오답입니다!",
            text: `(정답: ${text})`,
            width: 600,
            imageUrl: "/icons/quiz/ic_wrong.svg",
            showConfirmButton: false,
            showDenyButton: false,
            timer: 1000,
          }).then(() => {});
        })
        .catch((err) => {
          alert(err);
        });
    }
  };
  return (
    <NewsContainer>
      <div>
        <h2 style={{ margin: "0.5rem 0" }}>{article.title}</h2>
        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
            position: "relative",
          }}
        >
          <div style={{ fontSize: "0.75rem", color: grey }}>
            <span style={{ color: primary }}>{article.kind}</span>
            <span>{article.company} </span>
            <span>{article.issueDate}</span>
          </div>
          <div
            style={{
              display: "flex",
              alignItems: "center",
              position: "relative",
            }}
          >
            <Icon>
              <img src="/icons/news/ic_read.svg" />
            </Icon>
            <Icon>
              <img
                src="/icons/news/ic_size.svg"
                onClick={() => setFontSizeModal(!fontSizeModal)}
              />
            </Icon>
            <Icon>
              <img
                src="/icons/news/ic_summary.svg"
                onClick={() => setSummaryModal(!summaryModal)}
              />
            </Icon>
            <Icon onClick={handleBookmark}>
              {scraped ? (
                <img src="/icons/news/ic_bookmark_filled.svg" />
              ) : (
                <img src="/icons/news/ic_bookmark_empty.svg" />
              )}
            </Icon>
            {fontSizeModal && (
              <SizeBubble
                setFontSize={setFSize}
                fSize={fSize}
                setFontSizeModal={setFontSizeModal}
              />
            )}
          </div>
          {summaryModal && <SummaryBubble summary={article.summary} />}
        </div>
        <div
          style={{
            flex: 1,
            height: "1px",
            backgroundColor: white_grey,
            margin: "1.5rem 0",
          }}
        />
      </div>
      {/* <div dangerouslySetInnerHTML={{ __html: article.content }} /> */}
      <SafeHtml content={article.content} />
      <div
        style={{
          flex: 1,
          height: "1px",
          backgroundColor: white_grey,
          margin: "1.5rem 0",
        }}
      />
      {Object.keys(quiz).length === 0 ? (
        <></>
      ) : (
        <QuizContainer>
          <div>{quiz.question}</div>
          <input type="text" onChange={(e) => setText(e.target.value)} />
          <button onClick={mark}>제출하기</button>
        </QuizContainer>
      )}
    </NewsContainer>
  );
}

const NewsContainer = styled.div`
  background-color: ${white};
  border: 1px solid ${lightest_grey};
  border-radius: 1rem;
  flex: 11;
  padding: 3rem 3.5rem;
`;

const Icon = styled.button`
  background-color: ${white};
  border: 0;
  outline: 0;
  cursor: pointer;
  height: 100%;
`;

const QuizContainer = styled.div`
  border-radius: 16px;
  border: 1px solid ${lightest_grey};
  width: 996px;
  font-size: 16px;
`;
