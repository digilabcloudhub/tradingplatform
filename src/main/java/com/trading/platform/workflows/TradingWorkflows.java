package com.trading.platform.workflows;

import java.util.Comparator;
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
import com.trading.platform.model.CancelOrder;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class TradingWorkflows {

	private static final String BUY = "BUY";
	private static final String SELL = "SELL";
	private static final String IN_PROGRESS = "IP";
	private static final String NOT_PROGRESS = "NP";

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private TradeDao tradeDao;

	@Autowired
	private InstrumentDao intrumentDao;

	public TradeEntity initateAddWorkflow(OrderEntity orderEntity) {
		orderEntity.setOrderStatus(IN_PROGRESS);
		OrderEntity workflowEntity = orderDao.save(orderEntity);
		TradeEntity tradeEntity = null;
		if (workflowEntity.getOrder_id() != null) {
			if (workflowEntity.getOrder_type() == BUY) {
				tradeEntity = queryOrderType(SELL, workflowEntity);

			} else {
				tradeEntity = queryOrderType(BUY, workflowEntity);
			}

		}
		if (tradeEntity == null) {
			updateOrderWithNPStatus(workflowEntity);
			updateMarketValuetoLatest();
		}
		return tradeEntity;

	}

	private void updateMarketValuetoLatest() {
		List<OrderEntity>  buyOrder = orderDao.findByOrderTypeAndOrderStatus(BUY, NOT_PROGRESS);
		List<OrderEntity>  sellOrder = orderDao.findByOrderTypeAndOrderStatus(SELL, NOT_PROGRESS);
		Optional<OrderEntity> bestBuyOrder =buyOrder.stream().max(Comparator.comparing(OrderEntity :: getOrder_price));
		Optional<OrderEntity> bestSellOrder = sellOrder.stream().max(Comparator.comparing(OrderEntity :: getOrder_price));
		//Double updatedMarketValue = ( + sellOrder.getOrder_price()) / 2;
		if (bestBuyOrder.isPresent() &&  bestSellOrder.isPresent()) {
			Double updatedMarketValue =(bestBuyOrder.get().getOrder_price()+bestSellOrder.get().getOrder_price())/2;
			if(updatedMarketValue>0) {
				List<InstrumentEntity> instruments = intrumentDao.findAll();
				InstrumentEntity instrument = instruments.get(0);
				instrument.builder().setMarket_price(updatedMarketValue);
				intrumentDao.save(instrument);
			}
			
		}

	}

	public String initateCancelWorkflow(CancelOrder order) {
		OrderEntity workflowEntity = orderDao.findByOrderIdAndOrderStatus(order.getOrder_id(), NOT_PROGRESS);
		if (workflowEntity != null) {
			orderDao.delete(workflowEntity);
			return "Success";
		} else {
			return "Order cannot be deleted.As it is already processed";
		}

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

	private void updateOrderWithNPStatus(OrderEntity entity) {
		System.out.println("Inside not traded order");
		entity.setOrderStatus("NP");
		orderDao.save(entity);

	}

	private void updateOrders(TradeEntity entity) {
		System.out.println("Inside update order");
		OrderEntity buyOrder = orderDao.getById(entity.getBuy_order_id());
		OrderEntity sellOrder = orderDao.getById(entity.getSell_order_id());
		buyOrder.setOrderStatus("P");
		buyOrder.setTrade_id(entity.getTrade_id());
		sellOrder.setOrderStatus("P");
		sellOrder.setTrade_id(entity.getTrade_id());
		orderDao.save(buyOrder);
		orderDao.save(sellOrder);

	}

	private static <T> Optional<T> getFirstElement(List<T> orderList) {
		if (orderList != null && !orderList.isEmpty()) {
			return Optional.of(orderList.get(0));
		}

		return Optional.empty();

	}

	private TradeEntity queryOrderType(String order_type, OrderEntity workflowEntity) {
		List<OrderEntity> listOfSellOrders = orderDao.findByOrderTypeAndQuantityAndOrderStatus(order_type,
				workflowEntity.getQuantity(), IN_PROGRESS);
		Optional<OrderEntity> sellOrder = getFirstElement(listOfSellOrders);
		if (sellOrder.isPresent()) {
			return initiateTrade(workflowEntity, sellOrder.get());
		}
		return null;
	}

}
