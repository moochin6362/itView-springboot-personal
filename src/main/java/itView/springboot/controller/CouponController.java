package itView.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import itView.springboot.service.CouponService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {
	private final CouponService cService;
}
