//삭제하기 클릭 시 1차 모달 삭제여부 질문 + 2차모달 예/아니오 확정
document.getElementById('delete-btn').addEventListener('click', function(){
    document.getElementById('deleteModal').style.display="flex";
    document.getElementById('deleteBoard').innerText = "삭제하시겠습니까?"
});

//예 => 글 삭제
document.getElementById('delete').addEventListener('click', function(){
    //게시글No 가져와서 해당 글 삭제하기
    const boardId = document.getElementById('proBoardDetail').value;

    fetch('/admin/deleteProBoard', {
        method:"POST",
        headers : {
            "content-Type" : "application/json"
        },
        body : JSON.stringify({id : boardId})
    })
	then(res=>{
		if(res.ok){
			alert('해당 공지를 삭제하였습니다.');
			window.location.href = "/admin/proBoard"; //금지사항 게시판 목록으로 이동하기
		} else {
			alert('삭제에 실패하였습니다.');
		}
	})
	.catch(err => console.log(err));
});
    
//아니요 => 모달 닫기
document.getElementById('dontDelete').addEventListener('click', function(){
	const closeModal= document.querySelector(".close");
	closeModal.style.display = "none";
});

