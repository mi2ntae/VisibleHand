import React, {useEffect} from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { setUser } from "reducer/userReducer";
import http from "api/commonHttp";

export default function Google() {
  const user = useSelector((state) => state.user);
  console.log(user);

  const dispatch = useDispatch();   

  useEffect(() => {
      const code = new URL(window.location.href).searchParams.get("code");

      if(code) {
         http
        .get("user/auth/google?code=" + code)
        .then(({ data }) => {
          dispatch(setUser(data));
        })
        .catch((err) => {
          console.log(err);
        }); 
      }
      
    }, []);

  return (
      <div>
      </div>
  );
}