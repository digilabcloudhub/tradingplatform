package com.trading.platform.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
	@NotNull
	private Long order_id;
	@NotNull
	private Long trade_id;
	@NotNull
	private OrderType order_type;
	public OrderType getOrder_type() {
		return order_type;
	}
	private Double order_price;
	public Long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
	public Long getTrade_id() {
		return trade_id;
	}
	public void setTrade_id(Long trade_id) {
		this.trade_id = trade_id;
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
	@NotNull
	private int quantity;

	

}
