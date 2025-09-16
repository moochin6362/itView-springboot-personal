function existReportUser(btn){
	const userNo = btn.getAttribute('data-user-no');
	
	fetch(`/admin/searchReportedUserDetail?userNo=${userNo}`)
		.then(response => response.json())
		.then(data => {
			if(data.existReport){
				//신고글 존재할 경우 회원 상세페이지
				window.location.href=`/admin/rUserDetail?userNo=${userNo}`;
			} else {
				alert('신고글이 없습니다.');
				location.reload();
			}
		})
		.catch(err => {
			console.error(err);
			alert('오류가 발생했습니다.');
		});
}