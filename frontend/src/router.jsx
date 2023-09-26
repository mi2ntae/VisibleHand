import { createBrowserRouter } from "react-router-dom";
import App from "./App";
import Error from "./pages/Error";
import Home from "./pages/Home";
import News from "./pages/News";
import Feed from "./pages/Feed";
import Dictionary from "./pages/Dictionary";
import Quiz from "./pages/Quiz";
import QuizSolve from "components/Quiz/QuizSolve";
import Mypage from "./pages/Mypage";
import NewsDetail from "pages/NewsDetail";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    errorElement: <Error />,
    children: [
      { index: true, path: "/", element: <Home /> },
      { path: "/news", element: <News /> },
      { path: "/news/:articleId", element: <NewsDetail /> },
      { path: "/feed", element: <Feed /> },
      { path: "/dictionary", element: <Dictionary /> },
      {
        path: "/quiz",
        children: [
          {
            path: "",
            element: <Quiz />,
          },
          {
            path: "solve",
            element: <QuizSolve retry={false} />,
          },
          {
            path: "retry",
            element: <QuizSolve retry={true} />,
          },
        ],
      },
      { path: "/mypage/:userId", element: <Mypage /> },
    ],
  },
]);

export default router;