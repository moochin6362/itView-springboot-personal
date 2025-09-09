document.addEventListener('DOMContentLoaded', () => {
    const sidebar = document.querySelector('.sidebar');
    if (!sidebar) return;

    sidebar.addEventListener('click', (e) => {
        // 1️⃣ 아이콘 클릭
        const icon = e.target.closest('.icon-bar i');
        if (icon) {
            const targetMenu = icon.dataset.menu;

            sidebar.querySelectorAll('.menu-item').forEach(item => {
                const submenu = item.nextElementSibling;
                if (item.dataset.menu === targetMenu) {
                    // 해당 메뉴만 토글
                    const isActive = item.classList.contains('active');
                    item.classList.toggle('active', !isActive);
                    if (submenu) submenu.style.display = !isActive ? 'block' : 'none';
                } else {
                    // 다른 메뉴는 닫기
                    item.classList.remove('active');
                    if (submenu) submenu.style.display = 'none';
                }
            });

            // 아이콘 active 상태 갱신
            sidebar.querySelectorAll('.icon-bar i').forEach(i => i.classList.remove('active'));
            icon.classList.add('active');

            return;
        }

        // 2️⃣ 메뉴 클릭
        const menuItem = e.target.closest('.menu-item');
        if (menuItem) {
            const submenu = menuItem.nextElementSibling;
            const isActive = menuItem.classList.contains('active');

            // 모든 메뉴 닫기
            sidebar.querySelectorAll('.menu-item').forEach(item => {
                item.classList.remove('active');
                const sub = item.nextElementSibling;
                if (sub) sub.style.display = 'none';
            });

            // 클릭한 메뉴만 토글
            if (!isActive) {
                menuItem.classList.add('active');
                if (submenu) submenu.style.display = 'block';
            }

            return;
        }

        // 3️⃣ 햄버거 버튼 클릭
        if (e.target.id === 'sidebarToggle') {
            sidebar.classList.toggle('hidden');
        }
    });
});
