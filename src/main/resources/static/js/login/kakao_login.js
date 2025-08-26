document.addEventListener('DOMContentLoaded', function() {
    if (!Kakao.isInitialized()) {
        Kakao.init("36c4525b1f76f6d071d405f80d50e8fa");
    }

	//fetch 비동기 카카오로그인
    Kakao.Auth.createLoginButton({
        container: '#kakao-login-btn',
        success: function(authObj) {
            fetch('/login/kakaoLogin', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: new URLSearchParams({ accessToken: authObj.access_token })
            })
            .then(async response => {
                const text = await response.text();
                let data = {};
                try {
                    data = text ? JSON.parse(text) : {};
                } catch (err) {
                    console.error('JSON 파싱 오류:', err, '응답 텍스트:', text);
                }

                if (response.ok && data.token) {
                    // 로그인 성공
                    localStorage.setItem('jwt', data.token);
                    window.location.href = '/index';
                } 
                else if (response.status === 401 && data.error === "가입되지 않은 카카오 계정입니다.") {
                    // 가입되지 않은 계정 → alert 후 회원가입 페이지 이동
                    alert('가입되지 않은 카카오 계정입니다. 회원가입 페이지로 이동합니다.');
                    window.location.href = '/login/signUp';
                } 
                else {
                    // 기타 오류
                    alert('로그인 실패: ' + (data.error || '알 수 없는 오류'));
                }
            })
            .catch(err => console.error('카카오 로그인 오류:', err));
        },
        fail: function(err) {
            console.error('카카오 SDK 오류:', err);
        }
    });
});
