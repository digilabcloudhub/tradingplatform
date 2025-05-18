package com.trading.platform.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_book")
@Getter
@Setter
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

	public OrderEntity() {

	}

	public OrderEntity(Long order_id, String order_type, Double order_price, int quantity) {
		this.orderId = order_id;
		this.orderType = order_type;
		this.orderPrice = order_price;
		this.quantity = quantity;

	}

}
