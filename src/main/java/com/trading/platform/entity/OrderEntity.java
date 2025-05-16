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
@Table(name = "order")
public class OrderEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long order_id;
	
	@Column(name = "trade_id")
	private Long trade_id;
	
	@NotNull
	@Column(name = "order_type")
	private String order_type;
	
	@Column(name = "order_price")
	private Double order_price;
	
	@Column(name = "order_status")
	private String order_status;
	
	@Column(name = "quantity")
	private int quantity;
	
	public Long getOrder_id() {
		return order_id;
	}

	public Long getTrade_id() {
		return trade_id;
	}

	public String getOrder_type() {
		return order_type;
	}

	public Double getOrder_price() {
		return order_price;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public String getOrder_status() {
		return order_status;
	}
	
	public OrderEntity() {

	}
	
	public OrderEntity(Long order_id, Long trade_id, String order_type, Double order_price, int quantity, String order_status) {
		this.order_id=order_id;
		this.trade_id=trade_id;
		this.order_type=order_type;
		this.order_price=order_price;
		this.quantity=quantity;
		this.order_status=order_status;
		
	}
	 public static OrderEntityBuilder builder() {
	        return new OrderEntityBuilder();
	    }

	public static class OrderEntityBuilder {

        private Long  order_id;
        private Long trade_id;
        private String order_type;
        private Double order_price;
        private String order_status;
        private int quantity;

        public OrderEntityBuilder setOrder_Id(final Long order_id) {
            this.order_id = order_id;
            return this;
        }

        public OrderEntityBuilder setTrade_id(final Long trade_id) {
            this.trade_id = trade_id;
            return this;
        }

        public OrderEntityBuilder setOrder_type(final String order_type) {
            this.order_type = order_type;
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
        
        public OrderEntityBuilder setOrder_status(final String order_status) {
            this.order_status = order_status;
            return this;
        }

        public OrderEntity build() {
            return new OrderEntity(order_id, trade_id, order_type, order_price, quantity,order_status);
        }

    }



}
