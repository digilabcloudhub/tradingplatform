package com.trading.platform.model;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class CancelOrder {

	@NotNull
	@Getter
	@Setter
	@Range(min=1)
	private Long order_id;

}
