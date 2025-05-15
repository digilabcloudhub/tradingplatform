package com.trading.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "order")
public class OrderEntity {
	
	private Long order_id;
	private Long trade_id;
	private String order_type;
	private Double order_price;
	private int quantity;

}
