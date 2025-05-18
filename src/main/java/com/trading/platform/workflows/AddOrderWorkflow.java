package com.trading.platform.workflows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.platform.dao.OrderDao;
import com.trading.platform.entity.OrderEntity;
import com.trading.platform.entity.TradeEntity;
import com.trading.platform.utility.Constants;

import jakarta.transaction.Transactional;

@Service
public class AddOrderWorkflow {

	@Autowired
	private  OrderDao orderDao;

	@Autowired
	private  TradingWorkflowsHelper tradingHelper;

	@Autowired
	private  PartialFullfillmentWorkflow partialWorkflow;

	/**
	initateAddWorkflow is called to initial add order
	@param OrderEntity
	@return TradeEntity
	*/
	@Transactional
	public TradeEntity initateAddWorkflow(OrderEntity orderEntity) {
		orderEntity.setOrderStatus(Constants.IN_PROGRESS);
		OrderEntity workflowEntity = orderDao.save(orderEntity);
		TradeEntity tradeEntity = null;
		if (workflowEntity.getOrderId() != null) {
			if (workflowEntity.getOrderType() == Constants.BUY) {
				tradeEntity = tradingHelper.queryOrderType(Constants.SELL, workflowEntity);
				if (tradeEntity == null) {
					tradeEntity = partialWorkflow.initatePartialFFWorkflow(Constants.SELL, workflowEntity);
				}
			} else {
				tradeEntity = tradingHelper.queryOrderType(Constants.BUY, workflowEntity);
				if (tradeEntity == null) {
					tradeEntity = partialWorkflow.initatePartialFFWorkflow(Constants.BUY, workflowEntity);
				}
			}

		}
		if (tradeEntity == null) {

			tradingHelper.updateOrderWithNPStatus(workflowEntity);
			tradingHelper.updateMarketValuetoLatest();
		}
		return tradeEntity;

	}

}
