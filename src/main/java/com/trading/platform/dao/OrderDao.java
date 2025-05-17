package com.trading.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trading.platform.entity.OrderEntity;


@Repository
public interface OrderDao  extends JpaRepository<OrderEntity, Long> {
	
	List<OrderEntity> findByOrderTypeAndQuantityAndOrderStatus(String order_type,int quantity,String orderStatus);
	OrderEntity findByOrderIdAndOrderStatus(Long order_id,String orderStatus);
	List<OrderEntity> findByOrderTypeAndOrderStatus(String order_type,String orderStatus);
	
}
