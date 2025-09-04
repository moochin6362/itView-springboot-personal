let confirmData = '';
    
// 인증 메일 발송
document.querySelector('.btn-verify').addEventListener('click', function() {
	const emailInput = document.querySelector('#email');
	const email = emailInput.value.trim();

    if (email === '') {
        alert('이메일이 입력되지 않았습니다.');
		emailInput.focus();
		return;
    } else {
        $.ajax({
            url: '/login/emailCheck',
            data: { email: email },
            success: data => {
                alert('메일이 발송되었습니다.');
                confirmData = data;
            },
            error: data => console.log(data)
        });
    }
});
    

// 인증번호 확인 (blur 이벤트 또는 입력 완료 후 버튼 누르지 않아도 가능하게 하려면 수정 가능)
document.querySelector('#userEmail-confirm').addEventListener('change', function() {
    const verifyNum = this.value.trim();
    const resultLabel = document.querySelector('#emailCheckResult');
	const userIdInput = document.querySelector('#userId');
	const userId = userIdInput.value.trim();  //userId 가져옴

    if (verifyNum === '') {
        alert("인증번호를 입력해주세요");
    } else if (confirmData === '') {
        alert("이메일 인증을 먼저 진행해주세요");
    } else {
        if (verifyNum === confirmData) {
            alert("이메일이 확인되었습니다.");
            this.disabled = true;
            resultLabel.textContent = "✓";
            resultLabel.style.color = "green";
			
			console.log("전달값:", userId);
			 //데이터 가지고 reset_password페이지로 넘기기
			 // userId만 쿼리스트링으로 전달
			 window.location.href = `/login/resetPwd?userId=${encodeURIComponent(userId)}`;
        } else {
            alert("인증번호가 일치하지 않습니다.");
            resultLabel.textContent = "인증실패";
            resultLabel.style.color = "red";
        }
    }
});
    