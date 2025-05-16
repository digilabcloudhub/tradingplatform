package com.trading.platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trading.platform.entity.OrderEntity;


@Repository
public interface OrderDao  extends JpaRepository<OrderEntity, Long> {

}
