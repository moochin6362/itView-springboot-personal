//제목 칸 (30글자로 자르고 ...으로 보이도록)
const titleCells = document.querySelectorAll("#admin-genBoard-table-body td:nth-child(2) a");
titleCells.forEach(cell => {
  const maxLength = 30; 
  if(cell.textContent.length > maxLength){
    cell.textContent = cell.textContent.slice(0, maxLength) + '...';
  }
});

//아이디 부분
const writerCells = document.querySelectorAll("#admin-genBoard-table-body td:nth-child(3)");
titleCells.forEach(cell => {
  const maxLength = 20; 
  if(cell.textContent.length > maxLength){
    cell.textContent = cell.textContent.slice(0, maxLength) + '...';
  }
});