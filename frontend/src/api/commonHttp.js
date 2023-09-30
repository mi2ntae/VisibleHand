import axios from "axios";
import { useEffect } from "react";
import store from "../store.js";
import { useDispatch } from 'react-redux';
import { setUser } from "reducer/userReducer";
import Swal from "sweetalert2";

const http = axios.create({
  baseURL: process.env.REACT_APP_HTTP_URL + "/api/",
});

const Interceptor = ({ children }) => {
  const dispatch = useDispatch();

  useEffect(() => {
    http.interceptors.request.use(
      function (config) {
        if (!config.url.includes("/token/")) {
          config.headers.Authorization = `Bearer ${store.getState().user.accessToken}`;
          return config;
        } else {
          delete config.headers.Authorization;
          return config;
        }
      },
      function (error) {
        Swal.fire({icon: 'error', title: "시스템 에러가 발생하였습니다. 관리자에게 문의하세요."})
        return Promise.reject(error);
      }
    );
    http.interceptors.response.use(
      (response) => {
        return response;
      },
      async (error) => {
        if (error.response.data.message === "만료된 토큰") {

        const user = store.getState().user;
        const userId = user.userId;

        await http
        .get( "user/auth/token/" + userId,
        { headers: { 'refreshToken': `${store.getState().user.refreshToken}` },
        })
        .then(response => {
          if(response.data.message==="토큰 재발급 완료") {
            const newAccessToken = response.data.tokenInfo.accessToken;
            const newRefreshToken = response.data.tokenInfo.refreshToken;
            console.log(newAccessToken);
            console.log(newRefreshToken);

            dispatch(setUser({
                  token: {
                    accessToken: newAccessToken,
                    refreshToken: newRefreshToken,
                  },
                  user: {
                    userId: user.userId,
                    nickname: user.nickname,
                    snsEmail: user.snsEmail,
                    provider: user.provider
                  }
              }));
              
              return error.config;
          }
        })
        } else if(error.response.status===500){
          Swal.fire({icon: 'error', title: "시스템 에러가 발생하였습니다. 관리자에게 문의하세요."})
          return Promise.reject(error);
        } else if(error.response.status===401){
          Swal.fire({icon: 'error', title: "로그인 후 다시 접속해주세요."})
          return Promise.reject(error);
        } 
      }
    );
  }, []);
  return children;
};

export { Interceptor };
export default http;
