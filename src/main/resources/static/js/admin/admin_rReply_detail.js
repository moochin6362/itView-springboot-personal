document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('rp-detailForm');
	const delBtn = document.getElementById('rp-deleteBtn');

	if (delBtn) {
	      delBtn.addEventListener('click', function(e) {
	          e.preventDefault(); 
	          if (confirm("댓글을 삭제하시겠습니까?")) {
	              form.method = "post";
	              form.action = '/admin/deleteReply';

	              // AJAX 제출
	              fetch(form.action, {
	                  method: 'POST',
	                  body: new FormData(form)
	              })
	              .then(response => {
	                  if (response.ok) {
						alert('댓글이 삭제되었습니다.');
	                    window.location.href = '/admin/rReply';
	                  } else {
	                      alert('댓글 삭제를 실패히였습니다');
	                  }
	              })
	              .catch(err => {
	                  console.error(err);
	                  alert('댓글 삭제 실패');
	              });
	          }
	      });
	  }
	  
});

