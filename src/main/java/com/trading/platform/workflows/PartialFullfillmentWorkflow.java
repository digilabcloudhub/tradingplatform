package com.trading.platform.workflows;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.trading.platform.dao.OrderDao;
import com.trading.platform.entity.OrderEntity;
import com.trading.platform.entity.TradeEntity;
import com.trading.platform.utility.Constants;

import jakarta.transaction.Transactional;

@Service
public class PartialFullfillmentWorkflow {

	@Autowired
	private  OrderDao orderDao;

	@Autowired
	private  TradingWorkflowsHelper tradingHelper;

	/**
	 * initatePartialFFWorkflow is called to for partial fullfillment flow
	 * 
	 * @param Order Type and Order Entity
	 * @return TradeEntity
	 */
	@Transactional
	public  TradeEntity initatePartialFFWorkflow(String orderType, OrderEntity partialEntity) {
		List<OrderEntity> listOfOrders = orderDao.findByOrderTypeAndOrderStatusAndOrderPrice(orderType,
				Constants.NOT_PROCESSED, partialEntity.getOrderPrice());
		TradeEntity tradeEntity = null;
		if (!CollectionUtils.isEmpty(listOfOrders)) {
			int orderQuantity = partialEntity.getQuantity();
			int initialQuantity = partialEntity.getQuantity();
			List<OrderEntity> completeOrders = new ArrayList<>();
			List<OrderEntity> partialOrders = new ArrayList<>();

			for (OrderEntity entity : listOfOrders) {
				if (initialQuantity > 0) {
					if (entity.getQuantity() < orderQuantity) {
						orderQuantity = orderQuantity - entity.getQuantity();
						partialEntity.setQuantity(orderQuantity);
						initialQuantity = orderQuantity;
						tradeEntity = initiatePFTrade(partialEntity, entity);
						if (tradeEntity != null) {
							entity.setTrade_id(tradeEntity.getTrade_id());
							completeOrders.add(entity);
						}

					} else {
						orderQuantity = entity.getQuantity() - orderQuantity;
						OrderEntity newOrder = createFullfilledOrder(entity, initialQuantity);
						entity.setQuantity(orderQuantity);
						partialOrders.add(entity);
						initialQuantity = 0;
						tradeEntity = initiatePFTrade(partialEntity, entity);
						if (tradeEntity != null) {
							partialEntity.setTrade_id(tradeEntity.getTrade_id());
							completeOrders.add(partialEntity);
							newOrder.setTrade_id(tradeEntity.getTrade_id());
							orderDao.save(newOrder);
						}
					}

				} else {
					break;
				}

			}

			if (partialEntity.getQuantity() > 0) {
				partialOrders.add(partialEntity);
			}
			updateOrders(partialOrders, completeOrders);
		}
		return tradeEntity;
	}

	private static OrderEntity createFullfilledOrder(OrderEntity order, int quantity) {
		OrderEntity newOrder = new OrderEntity();
		newOrder.setOrderType(order.getOrderType());
		newOrder.setOrderPrice(order.getOrderPrice());
		newOrder.setQuantity(quantity);
		newOrder.setOrderStatus(Constants.PROCESSED);
		return newOrder;

	}

	private  TradeEntity initiatePFTrade(OrderEntity partialEntity, OrderEntity order) {
		return tradingHelper.initiateTrade(partialEntity, order, Constants.PARTIAL_FULFILLED);
	}

	private  void updateOrders(List<OrderEntity> partialOrders, List<OrderEntity> completedOrders) {
		List<OrderEntity> updatedList = new ArrayList<>();
		for (OrderEntity entity : partialOrders) {
			entity.setOrderStatus(Constants.NOT_PROCESSED);
			updatedList.add(entity);
		}
		for (OrderEntity entity : completedOrders) {
			entity.setOrderStatus(Constants.PROCESSED);
			updatedList.add(entity);
		}

		orderDao.saveAll(updatedList);
		tradingHelper.updateMarketValuetoLatest();
	}

}
