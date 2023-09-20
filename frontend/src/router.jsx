import { createBrowserRouter } from "react-router-dom";
import App from "./App";
import Error from "./pages/Error";
import Home from "./pages/Home";
import News from "./pages/News";
import Feed from "./pages/Feed";
import Dictionary from "./pages/Dictionary";
import Quiz from "./pages/Quiz";
import Mypage from "./pages/Mypage";
import Login from "pages/Login";
import Kakao from "pages/Kakao";
import Google from "pages/Google";

const router = createBrowserRouter([
    {
        path: '/',
        element: <App />,
        errorElement: <Error />,
        children: [
            {index: true, path: '/', element: <Home />},
            {path: '/news', element: <News />},
            {path: '/feed', element: <Feed />},
            {path: '/dictionary', element: <Dictionary />},
            {path: '/quiz', element: <Quiz />},
            {path: '/mypage/:userId', element: <Mypage />},
            {path: '/login', element: <Login />},
            {path: '/auth/kakao', element: <Kakao />},
            {path: '/auth/google', element: <Google />}
        ]
    },
])

export default router;