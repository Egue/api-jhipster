package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EmailCampaignApi.
 */
@Entity
@Table(name = "email_campaign_api")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmailCampaignApi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "token")
    private String token;

    private String servicio;
    
    private String mail_envio;
    
    private String nombre_envio;
    
    private String html_part;
    
    // jhipster-needle-entity-add-field - JHipster will add fields here

    
    public String getHtml_part() {
		return html_part;
	}

	public void setHtml_part(String html_part) {
		this.html_part = html_part;
	}

	public Long getId() {
        return this.id;
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

	public EmailCampaignApi id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public EmailCampaignApi url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return this.token;
    }

    public EmailCampaignApi token(String token) {
        this.setToken(token);
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }
    

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailCampaignApi)) {
            return false;
        }
        return id != null && id.equals(((EmailCampaignApi) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

	@Override
	public String toString() {
		return "EmailCampaignApi [id=" + id + ", url=" + url + ", token=" + token + ", servicio=" + servicio
				+ ", mail_envio=" + mail_envio + ", nombre_envio=" + nombre_envio + "]";
	}

     
}
