package com.trading.platform.model;

public class InstrumentDetails {
	
	private String status;
	private String code;
	private String message;
	
	@Override
	public String toString() {
		return "InstrumentDetails [status=" + status + ", code=" + code + ", message=" + message+"]";
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
