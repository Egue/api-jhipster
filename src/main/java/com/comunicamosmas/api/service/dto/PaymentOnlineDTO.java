package com.comunicamosmas.api.service.dto;

import java.util.List;

public  class PaymentOnlineDTO {
    
    public static class Auth
    {
        private String url;

        private String login;

        private String token;
        
        private String url_download;

        private String url_list;

        

        

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

        public String getUrl_list() {
            return url_list;
        }

        public void setUrl_list(String url_list) {
            this.url_list = url_list;
        }

        
    }

    public static class ListPagos{
        private Integer id;

        private String client_name;

        private String client_document;

        private String status;

        private String response_checkout;

        private String response_event;

        private String facturas;

        private String created_at;

        private String update_at;

        private String response_checkout_requestid;

        private String reference;

        private Integer download;

        private String request_send;

        private Double total;

        

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getClient_name() {
            return client_name;
        }

        public void setClient_name(String client_name) {
            this.client_name = client_name;
        }

        public String getClient_document() {
            return client_document;
        }

        public void setClient_document(String client_document) {
            this.client_document = client_document;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getResponse_checkout() {
            return response_checkout;
        }

        public void setResponse_checkout(String response_checkout) {
            this.response_checkout = response_checkout;
        }

        public String getResponse_event() {
            return response_event;
        }

        public void setResponse_event(String response_event) {
            this.response_event = response_event;
        }

        public String getFacturas() {
            return facturas;
        }

        public void setFacturas(String facturas) {
            this.facturas = facturas;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getResponse_checkout_requestid() {
            return response_checkout_requestid;
        }

        public void setResponse_checkout_requestid(String response_checkout_requestid) {
            this.response_checkout_requestid = response_checkout_requestid;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public Integer getDownload() {
            return download;
        }

        public void setDownload(Integer download) {
            this.download = download;
        }

        public String getRequest_send() {
            return request_send;
        }

        public void setRequest_send(String request_send) {
            this.request_send = request_send;
        }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }

        public String getUpdate_at() {
            return update_at;
        }

        public void setUpdate_at(String update_at) {
            this.update_at = update_at;
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
