package com.trading.platform.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetails {

	private String status;
	private String code;
	private String message;

	@Override
	public String toString() {
		return "OrderDetails [status=" + status + ", code=" + code + ", message=" + message + "]";
	}

}
