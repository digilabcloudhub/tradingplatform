package com.trading.platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.platform.entity.InstrumentEntity;



public interface InstrumentDao  extends JpaRepository<InstrumentEntity, Long> {



}
