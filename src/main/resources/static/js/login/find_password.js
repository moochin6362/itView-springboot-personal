  const verifyBtn = document.getElementById("findPwd-verifyBtn");
  const useridInput = document.getElementById("userid");
  const userEmailInput = document.getElementById("userEmail");
  const emailConfirmInput = document.getElementById("userEmail-confirm");

  verifyBtn.addEventListener("click", () => {
    const userid = useridInput.value.trim();
    const email = userEmailInput.value.trim();
    const confirmCode = emailConfirmInput.value.trim();

    if (!userid || !email || !confirmCode) {
      alert("모든 항목을 입력해주세요.");
      return;
    }

    fetch("/user/verifyEmailCode", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        userid: userid,
        email: email,
        code: confirmCode
      })
    })
    .then(response => response.json())
    .then(data => {
      if (data.success) {
        alert("인증번호가 일치합니다.");
        // 인증 성공 시 자동 페이지 이동
        window.location.href = "/user/resetPassword"; // 이동할 주소
      } else {
        alert("인증번호가 일치하지 않습니다.");
      }
    })
    .catch(() => {
      alert("오류가 발생했습니다.");
    });
  });

