package com.comunicamosmas.api.service.dto;

public class MikrotikQueueSimpleDTO {
	 private String id;
	 private String comment;
	 private String disable;
	 private String droppend;
	 private String limitAt;
	 private String maxLimit;
	 private String name;
	 private String parent;
	 private String priory;
	 private String queue;
	 private String target;
	public MikrotikQueueSimpleDTO() {
		 
	}
	public MikrotikQueueSimpleDTO(String id, String comment, String disable, String droppend, String limitAt,
			String maxLimit, String name, String parent, String priory, String queue, String target) {
		super();
		this.id = id;
		this.comment = comment;
		this.disable = disable;
		this.droppend = droppend;
		this.limitAt = limitAt;
		this.maxLimit = maxLimit;
		this.name = name;
		this.parent = parent;
		this.priory = priory;
		this.queue = queue;
		this.target = target;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getDroppend() {
		return droppend;
	}
	public void setDroppend(String droppend) {
		this.droppend = droppend;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getPriory() {
		return priory;
	}
	public void setPriory(String priory) {
		this.priory = priory;
	}
	public String getQueue() {
		return queue;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	@Override
	public String toString() {
		return "MikrotikQueueSimpleDTO [id=" + id + ", comment=" + comment + ", disable=" + disable + ", droppend="
				+ droppend + ", limitAt=" + limitAt + ", maxLimit=" + maxLimit + ", name=" + name + ", parent=" + parent
				+ ", priory=" + priory + ", queue=" + queue + ", target=" + target + "]";
	}
	 
	
	 
}
