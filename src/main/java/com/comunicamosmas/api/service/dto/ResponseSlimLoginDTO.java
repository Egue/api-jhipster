package com.comunicamosmas.api.service.dto;

public class ResponseSlimLoginDTO {

	private boolean success;
	
	private String response;
	
	

	public ResponseSlimLoginDTO() {
		
 	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}
