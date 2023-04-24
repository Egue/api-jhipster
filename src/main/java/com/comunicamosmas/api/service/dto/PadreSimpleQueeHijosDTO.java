package com.comunicamosmas.api.service.dto;

import java.util.List;

import com.comunicamosmas.api.domain.MikrotikHijoSimpleQueue;

public class PadreSimpleQueeHijosDTO {
	
	
	private Integer id;
	private String name;
	private String target;
	private String comment;
	private String limitAt;
	private String maxLimit;
	private Integer reuso;
	private List<MikrotikHijoSimpleQueue> hijos;
	public PadreSimpleQueeHijosDTO() {
		 
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getLimitAt() {
		return limitAt;
	}
	public void setLimitAt(String limitAt) {
		this.limitAt = limitAt;
	}
	public String getMaxLimit() {
		return maxLimit;
	}
	public void setMaxLimit(String maxLimit) {
		this.maxLimit = maxLimit;
	}
	public Integer getReuso() {
		return reuso;
	}
	public void setReuso(Integer reuso) {
		this.reuso = reuso;
	}
	public List<MikrotikHijoSimpleQueue> getHijos() {
		return hijos;
	}
	public void setHijos(List<MikrotikHijoSimpleQueue> hijos) {
		this.hijos = hijos;
	}
	@Override
	public String toString() {
		return "PadreSimpleQueeHijosDTO [id=" + id + ", name=" + name + ", target=" + target + ", comment=" + comment
				+ ", limitAt=" + limitAt + ", maxLimit=" + maxLimit + ", reuso=" + reuso + ", hijos=" + hijos + "]";
	}
	
	
}
