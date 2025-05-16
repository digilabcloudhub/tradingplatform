package com.trading.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "trade")
public class TradeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trade_id")
	private Long trade_id;

	@Column(name = "buy_order_id")
	private Long buy_order_id;

	@Column(name = "sell_order_id")
	private Long sell_order_id;

	@Column(name = "market_price")
	private Double market_price;

	public TradeEntity() {

	}

	public TradeEntity(Long buy_order_id, Long sell_order_id, Double market_price) {
		this.buy_order_id = buy_order_id;
		this.sell_order_id = sell_order_id;
		this.market_price = market_price;

	}

	public Long getBuy_order_id() {
		return buy_order_id;
	}

	public Long getSell_order_id() {
		return sell_order_id;
	}

	public Long getTrade_id() {
		return trade_id;
	}

	public static TradeEntityBuilder builder() {
		return new TradeEntityBuilder();
	}

	public static class TradeEntityBuilder {

		private Long buy_order_id;
		private Long sell_order_id;
		private Double market_price;

		public TradeEntityBuilder setBuy_order_id(final Long buy_order_id) {
			this.buy_order_id = buy_order_id;
			return this;
		}

		public TradeEntityBuilder setSell_order_id(final Long sell_order_id) {
			this.sell_order_id = sell_order_id;
			return this;
		}

		public TradeEntityBuilder setMarket_price(final Double market_price) {
			this.market_price = market_price;
			return this;
		}

		public TradeEntity build() {
			return new TradeEntity(buy_order_id, sell_order_id, market_price);
		}
	}

}
