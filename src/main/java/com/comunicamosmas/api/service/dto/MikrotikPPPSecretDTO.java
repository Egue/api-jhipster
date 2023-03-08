package com.comunicamosmas.api.service.dto;

public class MikrotikPPPSecretDTO {

	private String id;
	private String callerId;
	private String comment;
	private String disable;
	private String ipv6;
	private String lastCallerId;
	private String lastLoggedOut;
	private String name;
	private String profile;
	private String service;
	public MikrotikPPPSecretDTO() {
		 
	}
	public MikrotikPPPSecretDTO(String id, String callerId, String comment, String disable, String ipv6,
			String lastCallerId, String lastLoggedOut, String name, String profile, String service) {
		 
		this.id = id;
		this.callerId = callerId;
		this.comment = comment;
		this.disable = disable;
		this.ipv6 = ipv6;
		this.lastCallerId = lastCallerId;
		this.lastLoggedOut = lastLoggedOut;
		this.name = name;
		this.profile = profile;
		this.service = service;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getDisable() {
		return disable;
	}
	public void setDisable(String disable) {
		this.disable = disable;
	}
	public String getIpv6() {
		return ipv6;
	}
	public void setIpv6(String ipv6) {
		this.ipv6 = ipv6;
	}
	public String getLastCallerId() {
		return lastCallerId;
	}
	public void setLastCallerId(String lastCallerId) {
		this.lastCallerId = lastCallerId;
	}
	public String getLastLoggedOut() {
		return lastLoggedOut;
	}
	public void setLastLoggedOut(String lastLoggedOut) {
		this.lastLoggedOut = lastLoggedOut;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	@Override
	public String toString() {
		return "MikrotikPPPSecretDTO [id=" + id + ", callerId=" + callerId + ", comment=" + comment + ", disable="
				+ disable + ", ipv6=" + ipv6 + ", lastCallerId=" + lastCallerId + ", lastLoggedOut=" + lastLoggedOut
				+ ", name=" + name + ", profile=" + profile + ", service=" + service + "]";
	}
	
	
	
}
