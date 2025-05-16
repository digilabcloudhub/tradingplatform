package com.trading.platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.platform.entity.TradeEntity;

public interface TradeDao  extends JpaRepository<TradeEntity, Long> {
}


