import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Swal from "sweetalert2";

export default function Quiz() {
  const navigate = useNavigate();
  useEffect(() => {
    Swal.fire({
      title: "누르는 순간 퀴즈가 시작됩니다...!!!!!",
      imageUrl: "/icons/quiz/timer.png",
      imageHeight: 300,
      imageWidth: 300,
      showConfirmButton: true,
      confirmButtonText: "시작하기!",
      showDenyButton: false,
      showCancelButton: false,
    })
      .then((res) => {
        if (res.isConfirmed) {
          navigate("/quiz/solve");
        } else {
          navigate("/news");
        }
      })
      .catch((err) => alert(err));
  });
  return <div>Quiz</div>;
}
