let confirmData = '';
    
// 인증 메일 발송
document.querySelector('.btn-verify').addEventListener('click', function() {
    const email = document.querySelector('#email').value.trim();

    if (email === '') {
        alert('이메일이 입력되지 않았습니다.');
        document.querySelector('#email').focus();
    } else {
        $.ajax({
            url: '/login/echeck',
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
        } else {
            alert("인증번호가 일치하지 않습니다.");
            resultLabel.textContent = "인증실패";
            resultLabel.style.color = "red";
        }
    }
});
    