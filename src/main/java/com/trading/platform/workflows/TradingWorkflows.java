package com.trading.platform.workflows;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.platform.dao.InstrumentDao;
import com.trading.platform.dao.OrderDao;
import com.trading.platform.dao.TradeDao;
import com.trading.platform.entity.InstrumentEntity;
import com.trading.platform.entity.OrderEntity;
import com.trading.platform.entity.TradeEntity;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class TradingWorkflows {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private TradeDao tradeDao;

	@Autowired
	private InstrumentDao intrumentDao;

	public TradeEntity initateWorkflow(OrderEntity orderEntity) {
		orderEntity.setOrderStatus("IP");
		OrderEntity workflowEntity = orderDao.save(orderEntity);
		TradeEntity tradeEntity =null;
		if (workflowEntity.getOrder_id() != null) {             System.out.println("OrderId is"+workflowEntity.getOrder_id());
             System.out.println("OrderId is"+workflowEntity.getOrder_id());
			if (workflowEntity.getOrder_type() == "BUY") {
				List<OrderEntity> listOfSellOrders = orderDao.findByOrderTypeAndQuantityAndOrderStatus("SELL",workflowEntity.getQuantity(), "IP"
						);
				Optional<OrderEntity> sellOrder = getFirstElement(listOfSellOrders);
				if (sellOrder.isPresent()) {
					tradeEntity=initiateTrade(workflowEntity, sellOrder.get());
				}

			}

		}
		return tradeEntity;

	}

	private TradeEntity initiateTrade(OrderEntity buyOrder, OrderEntity sellOrder) {
		List<InstrumentEntity> instruments = intrumentDao.findAll();
		TradeEntity tradeEntity = null;
		if (instruments != null && !instruments.isEmpty()) {
			tradeEntity = tradeDao.save(TradeEntity.builder().setBuy_order_id(buyOrder.getOrder_id())
					.setSell_order_id(sellOrder.getOrder_id()).setMarket_price(instruments.get(0).getMarket_price())
					.build());
		}
		if (tradeEntity != null && tradeEntity.getTrade_id() != null) {
			updateOrders(tradeEntity);
		}
		return tradeEntity;
	}

	private void updateOrders(TradeEntity entity) {
	     System.out.println("Inside update order");
		OrderEntity buyOrder = orderDao.getById(entity.getBuy_order_id());
		OrderEntity sellOrder = orderDao.getById(entity.getSell_order_id());
		buyOrder.setOrderStatus("P");
		buyOrder.setTrade_id(entity.getTrade_id());
		sellOrder.setOrderStatus("P");
		sellOrder.setTrade_id(entity.getTrade_id());
		 System.out.println("Printing orderStatus"+ buyOrder.getOrder_status() );
		 System.out.println("Printing sellorderStatus"+ sellOrder.getOrder_status() );
		orderDao.save(buyOrder);
		orderDao.save(sellOrder);

	}

	private static <T> Optional<T> getFirstElement(List<T> orderList) {
		if (orderList != null && !orderList.isEmpty()) {
			return Optional.of(orderList.get(0));
		}

		return Optional.empty();

	}

}
