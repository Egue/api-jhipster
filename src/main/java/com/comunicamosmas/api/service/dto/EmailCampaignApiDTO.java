package com.comunicamosmas.api.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.comunicamosmas.api.domain.EmailCampaignApi} entity.
 */
public class EmailCampaignApiDTO implements Serializable {

    private Long id;

    private String url;

    private String token;
    
    private String servicio;
    
    private String mail_envio;
    
    private String nombre_envio;
    
    private String html_part;
    
    

    public String getHtml_part() {
		return html_part;
	}

	public void setHtml_part(String html_part) {
		this.html_part = html_part;
	}

	public String getMail_envio() {
		return mail_envio;
	}

	public void setMail_envio(String mail_envio) {
		this.mail_envio = mail_envio;
	}

	public String getNombre_envio() {
		return nombre_envio;
	}

	public void setNombre_envio(String nombre_envio) {
		this.nombre_envio = nombre_envio;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailCampaignApiDTO)) {
            return false;
        }

        EmailCampaignApiDTO emailCampaignApiDTO = (EmailCampaignApiDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, emailCampaignApiDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

	@Override
	public String toString() {
		return "EmailCampaignApiDTO [id=" + id + ", url=" + url + ", token=" + token + ", servicio=" + servicio + "]";
	}

     
}
