
const title = document.querySelectorAll("#admin-partBoard-table-body td:nth-child(2)");
title.forEach(cell => {
  const maxLength = 30; 
  if(cell.textContent.length > maxLength){
    cell.textContent = cell.textContent.slice(0, maxLength) + '...';
  }
});
const writer = document.querySelectorAll("#admin-partBoard-table-body td:nth-child(3)");
title.forEach(cell => {
  const maxLength = 20; 
  if(cell.textContent.length > maxLength){
    cell.textContent = cell.textContent.slice(0, maxLength) + '...';
  }
});