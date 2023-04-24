package com.comunicamosmas.api.service.dto;

import java.time.Instant;

public class userLoginDTO {
	
	private Long id;
	private String login;
	private String firtsName;
	private String lastName;
	private String email;
	private Boolean activated;
	private String langKey;
	private String imageUrl;
	private Instant resetDate;
	private String rol;
	
	public userLoginDTO() {
		 
	}

	public userLoginDTO(Long id, String login, String firtsName, String lastName, String email, Boolean activated,
			String langKey, String imageUrl, Instant resetDate , String rol) {
		 
		this.id = id;
		this.login = login;
		this.firtsName = firtsName;
		this.lastName = lastName;
		this.email = email;
		this.activated = activated;
		this.langKey = langKey;
		this.imageUrl = imageUrl;
		this.resetDate = resetDate;
		this.setRol(rol);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getFirtsName() {
		return firtsName;
	}

	public void setFirtsName(String firtsName) {
		this.firtsName = firtsName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public String getLangKey() {
		return langKey;
	}

	public void setLangKey(String langKey) {
		this.langKey = langKey;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Instant getResetDate() {
		return resetDate;
	}

	public void setResetDate(Instant resetDate) {
		this.resetDate = resetDate;
	}
	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
	

	@Override
	public String toString() {
		return "userLoginDTO [id=" + id + ", login=" + login + ", firtsName=" + firtsName + ", lastName=" + lastName
				+ ", email=" + email + ", activated=" + activated + ", langKey=" + langKey + ", imageUrl=" + imageUrl
				+ ", resetDate=" + resetDate +", rol= "+ rol +"]";
	}

	
	
	

}
