package com.trading.platform.model;

import jakarta.validation.constraints.NotNull;

public class Order {
	@NotNull
	private Long order_id;
	@NotNull
	private Long trade_id;
	@NotNull
	private OrderType order_type;
	private Double order_price;
	@NotNull
	private int quantity;
	

}
