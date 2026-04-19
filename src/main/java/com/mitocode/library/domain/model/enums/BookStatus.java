package com.mitocode.library.domain.model.enums;

public enum BookStatus {
	
	AVAILABLE("Available"),
	BORROWED("Borrowed");
	
	private final String status;

	BookStatus(String status) {
		this.status = status;
	}

	public String getSatus() {
		return this.status;
	}

}
