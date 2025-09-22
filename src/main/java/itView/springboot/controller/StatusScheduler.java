package itView.springboot.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import itView.springboot.service.ShoppingService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StatusScheduler {
	private final ShoppingService sService;
	
	
	@Scheduled(initialDelay = 600000, fixedRate = 600000)
	public void autoConfirmOrder() {
		int update=sService.updateAutoConfirmOrder();  
		
		
	}
	
	
	@Scheduled(initialDelay = 20000, fixedRate = 20000)
	public void autoUpdateDelivery() {
		
		int update=sService.updateAutoUpdateDelivery();
		
	}
}
