package com.comunicamosmas.api.service.dto;

import java.io.Serializable;
import java.util.List;

public class FacturasControlmasDTO{

    public static class FacturaSendMail{
        private Long id_cliente;

        private String correo;

        private FacturasDebe facturas;

        public Long getId_cliente() {
            return id_cliente;
        }

        public void setId_cliente(Long id_cliente) {
            this.id_cliente = id_cliente;
        }

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public FacturasDebe getFacturas() {
            return facturas;
        }

        public void setFacturas(FacturasDebe facturas) {
            this.facturas = facturas;
        }

        
    }

    public static class FacturasPendientes implements Serializable{
 
        private static final long serialVersionUID = 7123131311L;
        private Long id_cliente; 

        private String documento;

        private String nombres;

        private String telefono;

        private String correo;

        private List<FacturasDebe> facturas;

        public Long getId_cliente() {
            return id_cliente;
        }

        public void setId_cliente(Long id_cliente) {
            this.id_cliente = id_cliente;
        }

        public String getDocumento() {
            return documento;
        }

        public void setDocumento(String documento) {
            this.documento = documento;
        }

        public String getNombres() {
            return nombres;
        }

        public void setNombres(String nombres) {
            this.nombres = nombres;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public List<FacturasDebe> getFacturas() {
            return facturas;
        }

        public void setFacturas(List<FacturasDebe> facturas) {
            this.facturas = facturas;
        }

        
    }

    public static class SumaTotales{
        private double total;

        private double parcial;

        

        public SumaTotales(double total, double parcial) {
            this.total = total;
            this.parcial = parcial;
        }

        public double getTotal() {
            return total;
        }

        public double getParcial() {
            return parcial;
        }

        public void setTotal(double total) {
            this.total += total;
        }

        

        public void setParcial(double parcial) {
            this.parcial += parcial;
        }

        
    }

    
    public static class FacturasDebe{
 
        private Long id_empresa;
        
        private Long facturado_mes;

        private Long factura;

        private String lado;

        private String concepto;

        private String valor;

        

        public Long getId_empresa() {
            return id_empresa;
        }

        public void setId_empresa(Long id_empresa) {
            this.id_empresa = id_empresa;
        }

        public Long getFacturado_mes() {
            return facturado_mes;
        }

        public void setFacturado_mes(Long facturado_mes) {
            this.facturado_mes = facturado_mes;
        }

        public Long getFactura() {
            return factura;
        }

        public void setFactura(Long factura) {
            this.factura = factura;
        }

        public String getLado() {
            return lado;
        }

        public void setLado(String lado) {
            this.lado = lado;
        }

        public String getConcepto() {
            return concepto;
        }

        public void setConcepto(String concepto) {
            this.concepto = concepto;
        }

        public String getValor() {
            return valor;
        }

        public void setValor(String valor) {
            this.valor = valor;
        }

        
    }
}   