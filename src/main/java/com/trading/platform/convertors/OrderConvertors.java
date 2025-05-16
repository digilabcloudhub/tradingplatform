package com.trading.platform.convertors;

import java.util.OptionalDouble;

import com.trading.platform.entity.InstrumentEntity;
import com.trading.platform.entity.OrderDetails;
import com.trading.platform.entity.OrderEntity;
import com.trading.platform.model.FinancialInstrument;
import com.trading.platform.model.InstrumentDetails;
import com.trading.platform.model.Order;

public class OrderConvertors {

	public static OrderEntity orderConvertor(Order order) {

		return OrderEntity.builder()
				.setOrder_type(order.getOrder_type().toString())
				.setOrder_price(OptionalDouble.of(order.getOrder_price()).orElse(0))
				.setQuantity(order.getQuantity()).build();

	}
	
	public static OrderDetails convertResponse(OrderEntity orderEntity) {
		
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setCode("200");
		orderDetails.setStatus("Succefully created.");
		orderDetails.setMessage("Created order with order Id"+orderEntity.getOrder_id());
		return orderDetails;
		
		
	}
	
	public static InstrumentEntity convertInstrument(FinancialInstrument instrument) {

		return InstrumentEntity.builder().setMarket_price(instrument.getMarket_price()).setSymbol(instrument.getSymbol()).build();
		

	}
	
	public static InstrumentDetails convertResponse(InstrumentEntity instrumentEntity) {
		InstrumentDetails instrumentDetails = new InstrumentDetails();
		instrumentDetails.setCode("200");
		instrumentDetails.setStatus("Succefully created.");
		instrumentDetails.setMessage("Created order with instrument Id"+instrumentEntity.getIntrument_id());
		return instrumentDetails;
	}

}
