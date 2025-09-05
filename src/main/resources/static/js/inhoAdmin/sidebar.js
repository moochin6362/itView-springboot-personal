// 아이콘 클릭 시 해당 메뉴 펼치기
const icons = document.querySelectorAll('.icon-bar i');
const menuItems = document.querySelectorAll('.menu-item');
const sidebar = document.querySelector('.sidebar');
const toggleBtn = document.getElementById('sidebarToggle');

icons.forEach((icon) => {
    icon.addEventListener('click', () => {
        const targetMenu = icon.getAttribute('data-menu');

        // 모든 메뉴 초기화
        menuItems.forEach((item) => {
            if (item.dataset.menu === targetMenu) {
                item.classList.toggle('active');
            } else {
                item.classList.remove('active');
            }
        });

        // 아이콘 active 상태 갱신
        icons.forEach((i) => i.classList.remove('active'));
        icon.classList.add('active');
    });
});

// 메뉴 클릭 시 펼치기/접기
menuItems.forEach((item) => {
    item.addEventListener('click', () => {
        const submenu = item.nextElementSibling; // 바로 다음 .submenu 가져오기

        if (item.classList.contains('active')) {
            item.classList.remove('active');
            submenu.style.display = 'none';
        } else {
            menuItems.forEach((i) => {
                i.classList.remove('active');
                if (i.nextElementSibling) i.nextElementSibling.style.display = 'none';
            });
            item.classList.add('active');
            submenu.style.display = 'block';
        }
    });
});

// 햄버거 버튼으로 사이드바 슬라이드 토글
if (sidebar && toggleBtn) {
    toggleBtn.addEventListener('click', () => {
        sidebar.classList.toggle('hidden');
    });
}
