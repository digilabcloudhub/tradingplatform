package com.trading.platform.workflows;

import org.springframework.beans.factory.annotation.Autowired;
import com.trading.platform.dao.OrderDao;
import com.trading.platform.entity.OrderEntity;

import jakarta.transaction.Transactional;

@Transactional
public class TradingWorkflows {
	
	@Autowired
	private OrderDao orderDao;
	
	public  void initateWorkflow(OrderEntity orderEntity) {
		OrderEntity workflowEntity=orderDao.save(orderEntity);
		if(workflowEntity.getOrder_id()!=null) {
			if(workflowEntity.getOrder_type()=="BUY") {
				
			}
			
		}
		
		
		
	}

}
