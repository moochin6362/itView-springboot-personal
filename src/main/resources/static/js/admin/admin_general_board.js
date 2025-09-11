
const titleCells = document.querySelectorAll("#admin-genBoard-table-body td:nth-child(2)");
titleCells.forEach(cell => {
  const maxLength = 30; 
  if(cell.textContent.length > maxLength){
    cell.textContent = cell.textContent.slice(0, maxLength) + '...';
  }
});
const writerCells = document.querySelectorAll("#admin-genBoard-table-body td:nth-child(3)");
titleCells.forEach(cell => {
  const maxLength = 20; 
  if(cell.textContent.length > maxLength){
    cell.textContent = cell.textContent.slice(0, maxLength) + '...';
  }
});