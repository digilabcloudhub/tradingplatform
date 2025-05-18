package com.trading.platform.workflows;

import java.util.ArrayList;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.platform.dao.InstrumentDao;
import com.trading.platform.dao.OrderDao;
import com.trading.platform.dao.TradeDao;
import com.trading.platform.entity.InstrumentEntity;
import com.trading.platform.entity.OrderEntity;
import com.trading.platform.entity.TradeEntity;
import com.trading.platform.utility.Constants;

import jakarta.transaction.Transactional;

/**
 * TradingWorkflowsHelper class contains all helper method for trade and order
 * flow
 */
@Transactional
@Service
public class TradingWorkflowsHelper {

	private static Logger logger = LoggerFactory.getLogger(TradingWorkflowsHelper.class);

	@Autowired
	private  OrderDao orderDao;

	@Autowired
	private  TradeDao tradeDao;

	@Autowired
	private  InstrumentDao intrumentDao;

	public  void updateMarketValuetoLatest() {
		logger.info("Calling update market value");
		List<OrderEntity> buyOrder = orderDao.findByOrderTypeAndOrderStatus(Constants.BUY, Constants.NOT_PROCESSED);
		List<OrderEntity> sellOrder = orderDao.findByOrderTypeAndOrderStatus(Constants.SELL, Constants.NOT_PROCESSED);
		Optional<OrderEntity> bestBuyOrder = buyOrder.stream().max(Comparator.comparing(OrderEntity::getOrderPrice));
		Optional<OrderEntity> bestSellOrder = sellOrder.stream().max(Comparator.comparing(OrderEntity::getOrderPrice));
		if (bestBuyOrder.isPresent() && bestSellOrder.isPresent()) {
			Double updatedMarketValue = (bestBuyOrder.get().getOrderPrice() + bestSellOrder.get().getOrderPrice()) / 2;
			if (updatedMarketValue > 0) {
				List<InstrumentEntity> instruments = intrumentDao.findAll();
				InstrumentEntity instrument = instruments.get(0);
				instrument.setMarket_price(updatedMarketValue);
				intrumentDao.save(instrument);
			}

		}

	}

	public  TradeEntity initiateTrade(OrderEntity buyOrder, OrderEntity sellOrder, String partialFlag) {
		logger.info("Calling initiate trade method");
		List<InstrumentEntity> instruments = intrumentDao.findAll();
		TradeEntity tradeEntity = null;
		Long buyOrderId = buyOrder.getOrderId();
		Long sellOrderId = sellOrder.getOrderId();
		if (buyOrder.getOrderType() == Constants.SELL) {
			buyOrderId = sellOrder.getOrderId();
			sellOrderId = buyOrder.getOrderId();
		}
		if (instruments != null && !instruments.isEmpty()) {
			tradeEntity = tradeDao.save(TradeEntity.builder().setBuy_order_id(buyOrderId).setSell_order_id(sellOrderId)
					.setMarket_price(instruments.get(0).getMarket_price()).build());
		}
		if (tradeEntity != null && tradeEntity.getTrade_id() != null && partialFlag.equals("C")) {
			updateOrders(tradeEntity);
		}
		return tradeEntity;
	}

	public  void updateOrderWithNPStatus(OrderEntity entity) {
		logger.info("Calling order with NP status");
		entity.setOrderStatus(Constants.NOT_PROCESSED);
		orderDao.save(entity);

	}

	private  void updateOrders(TradeEntity entity) {
		OrderEntity buyOrder = orderDao.getById(entity.getBuy_order_id());
		OrderEntity sellOrder = orderDao.getById(entity.getSell_order_id());
		buyOrder.setOrderStatus(Constants.PROCESSED);
		buyOrder.setTrade_id(entity.getTrade_id());
		sellOrder.setOrderStatus(Constants.PROCESSED);
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

	public  TradeEntity queryOrderType(String order_type, OrderEntity workflowEntity) {
		List<OrderEntity> listOfSellOrders = orderDao.findByOrderTypeAndQuantityAndOrderStatusAndOrderPrice(order_type,
				workflowEntity.getQuantity(), Constants.NOT_PROCESSED, workflowEntity.getOrderPrice());
		Optional<OrderEntity> bestOrder = getFirstElement(listOfSellOrders);
		if (bestOrder.isPresent()) {
			return initiateTrade(workflowEntity, bestOrder.get(), "C");

		}
		return null;
	}

}
