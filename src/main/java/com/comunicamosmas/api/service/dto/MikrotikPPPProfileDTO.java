package com.comunicamosmas.api.service.dto;

public class MikrotikPPPProfileDTO {
	
	private String id;
	private String localAddress;
	private String name;
	private String rateLimit;
	private String remoteAddress;
	
	
	public MikrotikPPPProfileDTO() {
		 
	}
	
	public MikrotikPPPProfileDTO(String id, String localAddress, String name, String rateLimit, String remoteAddress) {
		 
		this.id = id;
		this.localAddress = localAddress;
		this.name = name;
		this.rateLimit = rateLimit;
		this.remoteAddress = remoteAddress;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLocalAddress() {
		return localAddress;
	}
	public void setLocalAddress(String localAddress) {
		this.localAddress = localAddress;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRateLimit() {
		return rateLimit;
	}
	public void setRateLimit(String rateLimit) {
		this.rateLimit = rateLimit;
	}
	public String getRemoteAddress() {
		return remoteAddress;
	}
	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	@Override
	public String toString() {
		return "MikrotikPPPProfileDTO [id=" + id + ", localAddress=" + localAddress + ", name=" + name + ", rateLimit="
				+ rateLimit + ", remoteAddress=" + remoteAddress + "]";
	}
	
	

}
