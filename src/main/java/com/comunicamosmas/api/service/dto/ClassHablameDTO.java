package com.comunicamosmas.api.service.dto;

public class ClassHablameDTO {

	private String toNumber;

    private String sms;

    private String flash;

    
	public ClassHablameDTO() {
		 
	}


	public ClassHablameDTO(String toNumber, String sms, String flash) {
	 
		this.toNumber = toNumber;
		this.sms = sms;
		this.flash = flash;
	}


	public String getToNumber() {
		return toNumber;
	}


	public void setToNumber(String toNumber) {
		this.toNumber = toNumber;
	}


	public String getSms() {
		return sms;
	}


	public void setSms(String sms) {
		this.sms = sms;
	}


	public String getFlash() {
		return flash;
	}


	public void setFlash(String flash) {
		this.flash = flash;
	}


	@Override
	public String toString() {
		return "ClassHablameDTO [toNumber=" + toNumber + ", sms=" + sms + ", flash=" + flash + "]";
	}
	
    
    
}
