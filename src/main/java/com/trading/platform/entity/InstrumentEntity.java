package com.trading.platform.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "instrument")
public class InstrumentEntity {
	
	public Long getIntrument_id() {
		return intrument_id;
	}

	public String getSymbol() {
		return symbol;
	}

	public Double getMarket_price() {
		return market_price;
	}

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
	
	public InstrumentEntity(String symbol,Double market_price) {
		this.symbol=symbol;
		this.market_price=market_price;
		
	}
	 public static InstrumentEntityBuilder builder() {
	        return new InstrumentEntityBuilder();
	    }
	
	public static class InstrumentEntityBuilder {


        private String symbol;
        private Double market_price;

        public InstrumentEntityBuilder setSymbol(final String symbol) {
            this.symbol = symbol;
            return this;
        }

        public InstrumentEntityBuilder setMarket_price(final Double market_price) {
            this.market_price = market_price;
            return this;
        }

        public InstrumentEntity build() {
            return new InstrumentEntity(symbol,market_price);
        }
	}

}
