document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('rv-detailForm');
	const delBtn = document.getElementById('rv-deleteBtn');

	if (delBtn) {
	      delBtn.addEventListener('click', function(e) {
	          e.preventDefault(); 
	          if (confirm("리뷰를 삭제하시겠습니까?")) {
	              form.method = "post";
	              form.action = '/admin/deleteReview';

	              // AJAX 제출
	              fetch(form.action, {
	                  method: 'POST',
	                  body: new FormData(form)
	              })
	              .then(response => {
	                  if (response.ok) {
						alert('리뷰가 삭제되었습니다.');
	                    window.location.href = '/admin/rReview';
	                  } else {
	                      alert('리뷰 삭제를 실패히였습니다');
	                  }
	              })
	              .catch(err => {
	                  console.error(err);
	                  alert('리뷰 삭제 실패');
	              });
	          }
	      });
	  }
	  
});

