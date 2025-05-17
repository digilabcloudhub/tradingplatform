package com.trading.platform.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trading.platform.exception.InvalidOrderIDException;
import com.trading.platform.model.CancelOrder;

public class CancelOrderValidator {

	private static Logger logger = LoggerFactory.getLogger(CancelOrderValidator.class);

	public static void validateOrder(CancelOrder order) throws InvalidOrderIDException{
		logger.info("inside validate order");
		if (order == null || order.getOrder_id() == null || order.getOrder_id() < 0) {
			throw new InvalidOrderIDException();

		}

	}

}
