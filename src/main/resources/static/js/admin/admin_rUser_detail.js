

document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('user-detailForm');
	const delBtn = document.getElementById('user-deleteBtn'); //삭제버튼

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


//회원 정지
document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('user-detailForm');
    const stopBtn = document.querySelector('.user-btn-submit'); // 정지 버튼

    if (stopBtn) {
        stopBtn.addEventListener('click', function(e) {
            e.preventDefault();
            // 선택된 정지 기간 가져오기
            const stopPeriod = form.querySelector('input[name="stopPeriod"]:checked')?.value;
            if (!stopPeriod) {
                alert("정지 기간을 선택해주세요.");
                return;
            }

            fetch('/admin/stopUser', {
                method: 'POST',
                body: new FormData(form)
            })
            .then(response => {
                if (response.ok) {
                    alert("회원 정지 완료");  
                    window.location.reload(); //정지 후 새로고침
                } else {
                    alert("회원 정지 실패");
                }
            })
            .catch(err => {
                console.error(err);
                alert("회원 정지 실패");
            });
        });
    }
});


