// /static/js/reportModal.js
window.openReportModal = function(type, id) {
  const modal = document.getElementById('reportModal');
  if (!modal) return console.error('reportModal element not found');
  document.getElementById('reportType').value = type;
  document.getElementById('reportTargetNo').value = id;
  document.getElementById('reportModal').style.display = 'flex';
};

window.closeReportModal = function() {
  const modal = document.getElementById('reportModal');
  if (modal) modal.style.display = 'none';
};









































//document.addEventListener('DOMContentLoaded', function() {
//  const form = document.getElementById('reportForm');
//  if (!form) return;
//
//  form.addEventListener('submit', function(e) {
//    e.preventDefault();
//    const fd = new FormData(this);
//    fetch('/inhoAdmin/enrollReport', {
//      method: 'POST',
//      body: new URLSearchParams(fd)
//    })
//    .then(res => res.text())
//    .then(txt => {
//      alert(txt === 'success' ? '신고가 접수되었습니다.' : '신고 실패');
//      closeReportModal();
//    })
//    .catch(err => console.error(err));
//  });
//});
