package com.trading.platform.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "trade_order")
public class OrderEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "trade_id")
	private Long trade_id;

	@NotNull
	@Column(name = "order_type")
	private String orderType;

	@Column(name = "order_price")
	private Double orderPrice;

	@Column(name = "order_status")
	private String orderStatus;

	@Column(name = "quantity")
	private int quantity;


	public Long getOrder_id() {
		return orderId;
	}

	public Long getTrade_id() {
		return trade_id;
	}

	public String getOrder_type() {
		return orderType;
	}

	public Double getOrder_price() {
		return orderPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getOrder_status() {
		return orderStatus;
	}

	public OrderEntity() {

	}

	public OrderEntity(Long order_id, String order_type, Double order_price, int quantity) {
		this.orderId = order_id;
		this.orderType = order_type;
		this.orderPrice = order_price;
		this.quantity = quantity;

	}

	public void setTrade_id(Long trade_id) {
		this.trade_id = trade_id;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public static OrderEntityBuilder builder() {
		return new OrderEntityBuilder();
	}

	public static class OrderEntityBuilder {

		private Long order_id;
		private String orderType;
		private Double order_price;
		private String order_status;
		private int quantity;

		public OrderEntityBuilder setOrder_Id(final Long order_id) {
			this.order_id = order_id;
			return this;
		}

		public OrderEntityBuilder setOrder_type(final String order_type) {
			this.orderType = order_type;
			return this;
		}

		public OrderEntityBuilder setOrder_price(final Double order_price) {
			this.order_price = order_price;
			return this;
		}

		public OrderEntityBuilder setQuantity(final int quantity) {
			this.quantity = quantity;
			return this;
		}


		public OrderEntity build() {
			return new OrderEntity(order_id, orderType, order_price, quantity);
		}

	}

}
