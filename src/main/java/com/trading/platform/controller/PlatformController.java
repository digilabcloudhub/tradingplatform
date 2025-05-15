package com.trading.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trading.platform.entity.OrderDetails;
import com.trading.platform.model.Order;
import com.trading.platform.service.PlatformService;

@RestController
@RequestMapping("cas")
public class PlatformController {

	@Autowired
	private PlatformService platformService;

	@PostMapping("/addOrder")
	public ResponseEntity<OrderDetails> addOrder(@RequestBody Order order) {

		OrderDetails addDetails=platformService.addOrder(order);
		return new ResponseEntity<OrderDetails>(addDetails,HttpStatus.CREATED);
		

	}
	
	@PostMapping("/cancelOrder")
	public ResponseEntity<OrderDetails> cancelOrder(@RequestBody Order order) {

		OrderDetails cancelDetails=platformService.cancelOrder(order);
		return new ResponseEntity<OrderDetails>(cancelDetails,HttpStatus.ACCEPTED);
		

	}
}
