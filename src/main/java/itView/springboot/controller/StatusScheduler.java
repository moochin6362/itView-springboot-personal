package itView.springboot.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import itView.springboot.service.ShoppingService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StatusScheduler {
	private final ShoppingService sService;
	
	
	@Scheduled(initialDelay = 30000, fixedRate = 30000)
	public void autoConfirmOrder() {
		int update=sService.updateAutoConfirmOrder();
		
		
	}
	
	
	@Scheduled(initialDelay = 30000, fixedRate = 30000)
	public void autoUpdateDelivery() {
		
		int update=sService.updateAutoUpdateDelivery();
		
	}
}
