document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('board-detailForm');
    const modal = document.getElementById('deleteModal');
	const delBtn = document.getElementById('board-deleteBtn');
    const modalBtn = document.getElementById('modalConfirmBtn');

	if (delBtn) {
	      delBtn.addEventListener('click', function(e) {
	          e.preventDefault(); 
	          if (confirm("정말 게시글을 삭제하시겠습니까?")) {
	              form.method = "post";
	              form.action = '/admin/deleteBoard';

	              // AJAX 제출
	              fetch(form.action, {
	                  method: 'POST',
	                  body: new FormData(form)
	              })
	              .then(response => {
					console.log("삭제 성공"); // <- 확인용
	                  if (response.ok) {
						alert('게시글이 삭제되었습니다.');
	                    window.location.href = '/admin/rBoard';
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

