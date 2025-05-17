package com.trading.platform.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstrumentDetails {

	private String status;
	private String code;
	private String message;

	@Override
	public String toString() {
		return "InstrumentDetails [status=" + status + ", code=" + code + ", message=" + message + "]";
	}
}
