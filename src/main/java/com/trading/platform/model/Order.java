package com.trading.platform.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
	@NotNull
	private OrderType order_type;
	@NotNull
	private int quantity;
	private Double order_price;
	
	public OrderType getOrder_type() {
		return order_type;
	}
	public Double getOrder_price() {
		return order_price;
	}
	public void setOrder_price(Double order_price) {
		this.order_price = order_price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	

}
