package com.trading.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.platform.convertors.OrderConvertors;
import com.trading.platform.dao.InstrumentDao;
import com.trading.platform.dao.OrderDao;
import com.trading.platform.entity.InstrumentEntity;
import com.trading.platform.entity.OrderDetails;
import com.trading.platform.entity.OrderEntity;
import com.trading.platform.entity.TradeEntity;
import com.trading.platform.model.FinancialInstrument;
import com.trading.platform.model.InstrumentDetails;
import com.trading.platform.model.Order;
import com.trading.platform.service.PlatformService;
import com.trading.platform.workflows.TradingWorkflows;

@Service
public class PlatformServiceImpl implements PlatformService {
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private InstrumentDao instrumentDao;
	
	@Autowired
	private TradingWorkflows trandingWorkflows;

	@Override
	public OrderDetails addOrder(Order order) {
		// TODO Auto-generated method stub
		TradeEntity trade=trandingWorkflows.initateWorkflow(OrderConvertors.orderConvertor(order));
		//OrderEntity orderEntity = orderDao.save(OrderConvertors.orderConvertor(order));
		if(trade!=null && trade.getTrade_id()!=null) {
			return OrderConvertors.convertTradeResponse(trade);
		}
		return OrderConvertors.convertOrderResponse();
	}

	@Override
	public OrderDetails cancelOrder(Order order) {
		// TODO Auto-generated method stub
		orderDao.save(null);
		return null;
	}

	@Override
	public InstrumentDetails addInstrument(FinancialInstrument instrument) {
		// TODO Auto-generated method stub
		
		InstrumentEntity instrumentEntity=instrumentDao.save(OrderConvertors.convertInstrument(instrument));
		if (instrumentEntity.getIntrument_id()!=null) {
			return OrderConvertors.convertResponse(instrumentEntity);
		}
		return null;
	}


}
