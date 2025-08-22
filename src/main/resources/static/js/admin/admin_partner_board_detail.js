document.getElementById("part-replySubmit").addEventListener("click", async () => {
  const content = document.getElementById("part-replyInput").value.trim();
  if (!content) return alert("내용을 입력하세요.");

  // 비동기 요청 (예: 댓글 등록 API)
  try {
    const res = await fetch("/api/reply", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ content }),
    });

    if (!res.ok) throw new Error("댓글 등록 실패");

    const newReply = await res.json();

    // 댓글 목록에 추가
    addReplyToListPartner(newReply);

    // 입력창 초기화
    document.getElementById("part-replyInput").value = "";
  } catch (err) {
    console.error(err);
    alert("댓글 등록 중 오류가 발생했습니다.");
  }
});

function addReplyToListPartner(reply) {
  const list = document.getElementById("part-replyList");
  const item = document.createElement("div");
  item.className = "partBoard-reply-item";
  item.textContent = reply.content;
  list.prepend(item); // 최신 댓글이 위로 오게
}
