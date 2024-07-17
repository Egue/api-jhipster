package com.comunicamosmas.api.service.dto;

import java.util.List;

public  class PaymentOnlineDTO {
    
    public static class Auth
    {
        private String url;

        private String login;

        private String token;
        
        private String url_download;

        

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

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getUrl_download() {
            return url_download;
        }

        public void setUrl_download(String url_download) {
            this.url_download = url_download;
        }

        
    }

    public static class PagosOnline{
        
        private String client_document; 

        private String reference;

        private List<Facturas> facturas;
  
        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }


        public String getClient_document() {
            return client_document;
        }

        public void setClient_document(String client_document) {
            this.client_document = client_document;
        }

        public List<Facturas> getFacturas() {
            return facturas;
        }

        public void setFacturas(List<Facturas> facturas) {
            this.facturas = facturas;
        }

        
    }

    public static class Facturas{
        
        private String factura;

        private String lado;

        private String id_deudas;

        private String concepto;

        private Double valor;

        public String getFactura() {
            return factura;
        }

        public void setFactura(String factura) {
            this.factura = factura;
        }

        public String getLado() {
            return lado;
        }

        public void setLado(String lado) {
            this.lado = lado;
        }

        public String getId_deudas() {
            return id_deudas;
        }

        public void setId_deudas(String id_deudas) {
            this.id_deudas = id_deudas;
        }

        public String getConcepto() {
            return concepto;
        }

        public void setConcepto(String concepto) {
            this.concepto = concepto;
        }

        public Double getValor() {
            return valor;
        }

        public void setValor(Double valor) {
            this.valor = valor;
        }

        
    }
}
