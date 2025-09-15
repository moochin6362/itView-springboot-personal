function addReply(reply) {
  const list = document.getElementById("part-replyList");
  const itemWrapper = document.createElement("div");
  itemWrapper.className = "partBoard-reply-list";
  
  const replyWriter = document.createElement("div");
  replyWriter.className = "partBoard-reply-admin";
  replyWriter.textContent = "관리자"; //관리자 답변은 이거로 고정
  
  const replyDate = document.createElement("div");
  replyDate.className = "partBoard-reply-date";
  
  const now = new Date();
  replyDate.textContent = now.toLocaleString(); //현재시간으로 추가
  
  
  const replyContent = document.createElement('div');
  replyContent.className = "partBoard-reply-text";
  replyContent.textContent = reply.replyContent;
  
  
  //작성자, 날짜, 내용 부분을 replyList div에 추가하기 (한묶음으로 추가하는 형식)
  itemWrapper.appendChild(replyWriter);
  itemWrapper.appendChild(replyDate);
  itemWrapper.appendChild(replyContent);
  //내림차순
  list.appendChild(itemWrapper);
}

document.getElementById("part-replySubmit").addEventListener("click", async () => {
  const boardId = document.getElementById('pBoardDetail').value;
  const content = document.getElementById("part-replyInput").value.trim();
  if (!content) return alert("내용을 입력하세요.");
  try {
    const res = await fetch(`/admin/pReply/${boardId}`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ replyContent: content }),
    });
    const newReply = await res.json();
    // 댓글 목록에 추가
    addReply(newReply);
	
    // 입력창 초기화
    document.getElementById("part-replyInput").value = "";
  } catch (error) {
    alert("댓글 등록에 실패했습니다.");
  }
});



//새로고침하면 댓글 달았던 것들이 사라져서 화면에 유지되도록!
async function loadReplies(boardId) {
  const res = await fetch(`/admin/pReplyList?boardId=${boardId}`);
  if (!res.ok) return;
  const replyList = await res.json();
  const list = document.getElementById('part-replyList');
  list.innerHTML = "";
  replyList.forEach(reply => addReply(reply));
}
document.addEventListener("DOMContentLoaded", () => {
  const boardId = document.getElementById('pBoardDetail').value;
  loadReplies(boardId);
});
