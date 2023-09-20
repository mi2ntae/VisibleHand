import React, {useEffect} from 'react';

export default function Google() {
    useEffect(() => {
        const code = new URL(window.location.href).searchParams.get("code");
        console.log(code);

        if (code) {
            fetch(process.env.REACT_APP_HTTP_URL + '/api/user/auth/google?code=' + code, {
                method: 'GET'
            })
            .then((response) => response.json())
            .then((data) => {
              const accessToken = data.token.accessToken;
              const refreshToken = data.token.refreshToken;
              console.log(accessToken);
              console.log(refreshToken);
              // dispatch({ type: 'setUser', payload: { accessToken: accessToken, refreshToken: refreshToken } });
          });
        }
      }, []);
    
      return (
        <div>
        </div>
      );
}