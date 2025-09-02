const passwordInput = document.getElementById("userPassword");
const toggleBtn = document.getElementById("togglePassword");

toggleBtn.addEventListener("click", () => {
  const type = passwordInput.getAttribute("type");
  if (passwordInput.type === "password") {
    passwordInput.setAttribute("type", "text");
    toggleBtn.innerHTML = '<i class="ti ti-eye-off"></i>';
  } else {
    passwordInput.setAttribute("type", "password");
    toggleBtn.innerHTML = '<i class="ti ti-eye"></i>';
  }
});


document.getElementById("findIdForm").addEventListener("submit", function(e){
    e.preventDefault();

    const formData = new FormData(this);

    fetch("/login/findId", {
        method: "POST",
        body: formData
    })
    .then(res => res.json())
    .then(data => {
        if(data.userId){
            document.getElementById("foundUserId").innerText = "회원님의 아이디는: " + data.userId + " 입니다.";
        } else {
            document.getElementById("foundUserId").innerText = "일치하는 아이디가 없습니다.";
        }
        document.getElementById("findIdModal").style.display = "flex";
    })
    .catch(err => console.error(err));
});

document.querySelector(".close").addEventListener("click", function(){
    document.getElementById("findIdModal").style.display = "none";
});

