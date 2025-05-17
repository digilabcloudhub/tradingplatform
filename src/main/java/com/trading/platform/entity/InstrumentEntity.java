package com.trading.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "instrument")
@Getter
@Setter
public class InstrumentEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "intrument_id")
	private Long intrument_id;

	@NotNull
	@Column(name = "symbol")
	private String symbol;

	@NotNull
	@Column(name = "market_price")
	private Double market_price;

	public InstrumentEntity() {

	}

	public InstrumentEntity(String symbol, Double market_price) {
		this.symbol = symbol;
		this.market_price = market_price;

	}

}
