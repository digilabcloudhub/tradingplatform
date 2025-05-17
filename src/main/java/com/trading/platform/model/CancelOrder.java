package com.trading.platform.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelOrder {
	

	@NotNull
	private Long order_id;
	@NotNull
	private OrderType order_type;
	@NotNull
	private int quantity;
	private Double order_price;
	
	public Long getOrder_id() {
		return order_id;
	}

}
