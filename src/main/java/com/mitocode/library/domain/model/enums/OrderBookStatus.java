package com.mitocode.library.domain.model.enums;

public enum OrderBookStatus {
	
	ACTIVE("Pending"),
	COMPLETED("Completed");
	
	private final String status;

	OrderBookStatus(String status) {
		this.status = status;
	}

	public String getSatus() {
		return this.status;
	}

}
