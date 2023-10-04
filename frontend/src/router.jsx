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
import Login from "pages/Login";
import Kakao from "pages/Kakao";
import Google from "pages/Google";
import SignUp from "pages/SignUp";
import ProfileSetting from "pages/ProfileSetting";
import Onboarding from "pages/Onboarding";
import ProfileUpdate from "pages/ProfileUpdate";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    errorElement: <Error />,
    children: [
      { index: true, path: "/", element: <Onboarding /> },
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
      { path: "/mypage", element: <Mypage /> },
      {path: '/login', element: <Login />},
      {path: '/auth/kakao', element: <Kakao />},
      {path: '/auth/google', element: <Google />},
      {path: '/profile', element: <ProfileSetting />},
      {path: '/signup', element: <SignUp />},
      {path: '/update', element: <ProfileUpdate />}
    ],
  },
]);

export default router;