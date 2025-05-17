package com.trading.platform.service;

import com.trading.platform.entity.OrderDetails;
import com.trading.platform.model.CancelOrder;
import com.trading.platform.model.FinancialInstrument;
import com.trading.platform.model.InstrumentDetails;
import com.trading.platform.model.Order;

public interface PlatformService {

	public OrderDetails addOrder(Order order);

	public OrderDetails cancelOrder(CancelOrder order);
	
	public InstrumentDetails addInstrument(FinancialInstrument instrument);

}
