window.onload = () => {
	const productDates = document.querySelectorAll('.productDate');
	for(const productDate of productDates){
		const fullDateStr = productDate.getAttribute('data-fullDate');
		if(!fullDateStr) return;
		
		const date = new Date(fullDateStr.replace(' ', 'T'));
		const dateDay = new Date(date.getFullYear(), date.getMonth(), date.getDate());
		const now = new Date();
		const nowDay = new Date(now.getFullYear(), now.getMonth(), now.getDate());
		
		if(nowDay > dateDay) {
			productDate.style.display = 'none';
		} else {
			productDate.innerText = '따끈따끈 신제품!';
		}
	}
}