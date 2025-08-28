// 아이콘 클릭 시 해당 메뉴 펼치기
const icons = document.querySelectorAll('.icon-bar i');
const menuItems = document.querySelectorAll('.menu-item');

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

// 오른쪽 메뉴 직접 클릭 시 펼치기
menuItems.forEach((item) => {
    item.addEventListener('click', () => {
        if (item.classList.contains('active')) {
            item.classList.remove('active');
        } else {
            menuItems.forEach((i) => i.classList.remove('active'));
            item.classList.add('active');
        }

        // 아이콘도 같이 active로
        icons.forEach((icon) => {
            if (icon.dataset.menu === item.dataset.menu) {
                icons.forEach((i) => i.classList.remove('active'));
                icon.classList.add('active');
            }
        });
    });
});
