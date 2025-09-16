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
