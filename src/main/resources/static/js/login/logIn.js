const passwordInput = document.getElementById("password");
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
