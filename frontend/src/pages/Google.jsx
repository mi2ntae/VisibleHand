import React, {useEffect} from 'react';

export default function Google() {
    // const code = new URL(window.location.href).searchParams.get("code");
    // console.log(code);

    useEffect(() => {
        const code = new URL(window.location.href).searchParams.get("code");
        console.log(code)
        if (code) {
          fetch('/api/auth/google?code=' + code, {
            method: 'GET'
        })
            .then(response => response.json())
            .then(message => {
                console.log("Response from backend:", message);
            })
            .catch(error => {
                console.error("Error sending code to backend:", error);
            });
        } else {
          console.log("Code not found in URL.");
        }
      }, []);
    
      return (
        <div>
          {/* Auth 컴포넌트의 내용을 정의하세요. */}
        </div>
      );
}