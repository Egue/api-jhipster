package com.comunicamosmas.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.apisupergiros")
public class ApiSupergiros {
    private String email;

    private String password;

    private String urldescargue;

    private String urlcargue;

    private String apiauth;

    private String deserializacion;

    public String getDeserializacion(){
        return deserializacion;
    }

    public void setDeserializacion(String deserializacion){
        this.deserializacion = deserializacion;
    }

    public String getApiauth(){
        return apiauth;
    }

    public void setApiauth(String apiauth){
        this.apiauth = apiauth;
    }

    public String getUrlcargue() {
        return urlcargue;
    }

    public void setUrlcargue(String urlcargue) {
        this.urlcargue = urlcargue;
    }

    public String getUrldescargue() {
        return urldescargue;
    }

    public void setUrldescargue(String urldescargue) {
        this.urldescargue = urldescargue;
    }




    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
