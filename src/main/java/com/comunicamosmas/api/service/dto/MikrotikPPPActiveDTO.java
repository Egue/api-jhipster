package com.comunicamosmas.api.service.dto;

public class MikrotikPPPActiveDTO {
	private String id;
	private String address;
	private String callerId;
	private String comment;
	private String name;
	private String service;
	private String uptime;
	
	public MikrotikPPPActiveDTO() {
	 
	}

	public MikrotikPPPActiveDTO(String id, String address, String callerId, String comment, String name, String service,
			String uptime) {
		super();
		this.id = id;
		this.address = address;
		this.callerId = callerId;
		this.comment = comment;
		this.name = name;
		this.service = service;
		this.uptime = uptime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCallerId() {
		return callerId;
	}

	public void setCallerId(String callerId) {
		this.callerId = callerId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	@Override
	public String toString() {
		return "MikrotikPPPActiveDTO [id=" + id + ", address=" + address + ", callerId=" + callerId + ", comment="
				+ comment + ", name=" + name + ", service=" + service + ", uptime=" + uptime + "]";
	}
	
	
	

}
