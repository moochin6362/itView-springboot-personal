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

const msg = /*[[${msg}]]*/ '';
    if (msg) {
        alert(msg);
    }
	
const beforeURL = document.referrer;
document.getElementsByName('beforeURL')[0].value = beforeURL;