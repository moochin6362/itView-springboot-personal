//비밀번호 일치확인
const userPwd = document.getElementById('newPwd'); //새 비번
const pwdConfirm = document.getElementById('newPwdConfirm'); //비번일치
const changePwBtn = document.getElementById('resetPwd-verifyBtn'); //변경 버튼
const changePwd = document.getElementById('changePwd'); //form

//비밀번호 일치 확인
changePwd.addEventListener('submit', function(event){
	if(pwdConfirm.value != userPwd.value){
		event.preventDefault(); //일치 안하면 폼 제출X
		alert('비밀번호가 일치하지 않습니다.');
		return false;
	}
	//비밀번호 특수문자, 글자 수 제한 X?
	
});

//변경 클릭 시 폼 제출 후 페이지 이동 => 로그인 화면


