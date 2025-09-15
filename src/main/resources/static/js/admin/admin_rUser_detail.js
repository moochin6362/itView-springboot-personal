

document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('user-detailForm');
    const modal = document.getElementById('deleteModal');
	const delBtn = document.getElementById('user-deleteBtn');
    const modalBtn = document.getElementById('modalConfirmBtn');

	if (delBtn) {
	      delBtn.addEventListener('click', function(e) {
	          e.preventDefault(); 
	          if (confirm("정말 회원을 삭제하시겠습니까?")) {
	              form.method = "post";
	              form.action = '/admin/deleteUser';

	              // AJAX 제출
	              fetch(form.action, {
	                  method: 'POST',
	                  body: new FormData(form)
	              })
	              .then(response => {
					console.log("삭제 성공"); // <- 확인용
	                  if (response.ok) {
	                      // 모달 띄우기
	                      modal.style.display = 'block';
	                  } else {
	                      alert('삭제 실패');
	                  }
	              })
	              .catch(err => {
	                  console.error(err);
	                  alert('삭제 실패');
	              });
	          }
	      });
	  }
	  
});

