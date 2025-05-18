package com.trading.platform.workflows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.platform.dao.OrderDao;
import com.trading.platform.entity.OrderEntity;
import com.trading.platform.model.CancelOrder;
import com.trading.platform.utility.Constants;

import jakarta.transaction.Transactional;

@Service
public class CancelOrderWorkflow {
	
	@Autowired
	private  OrderDao orderDao;
	
	@Transactional
	public String initateCancelWorkflow(CancelOrder order) {
		OrderEntity workflowEntity = orderDao.findByOrderIdAndOrderStatus(order.getOrder_id(), Constants.NOT_PROCESSED);
		if (workflowEntity != null) {
			orderDao.delete(workflowEntity);
			return "Success";
		} else {
			return "Order cannot be deleted.As it is already processed";
		}

	}

}
