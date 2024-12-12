package com.comunicamosmas.api.service.dto;

import java.util.List;

public class InfoFacturaDTO {
    
    private Cliente cliente;
    private Factura factura;
    private Empresa empresa;
    private List<Deudas> deudas;
    private Double subTotal;
    private Double iva;
    private Double totalMes;
    private String saldoAnterior;
    private Double pagosaCuenta;
    private String Total;

    

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Deudas> getDeudas() {
        return deudas;
    }

    public void setDeudas(List<Deudas> deudas) {
        this.deudas = deudas;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public String getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(String saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public Double getPagosaCuenta() {
        return pagosaCuenta;
    }

    public void setPagosaCuenta(Double pagosaCuenta) {
        this.pagosaCuenta = pagosaCuenta;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public static class Factura{
        private String origen;
        private String numero;
        private String emision;
        private String fechaCorte;
        private String fechaLimite;
        private String serializable;        
        private Resolucion resolucion;

        

        public String getNumero() {
            return numero;
        }
        public void setNumero(String numero) {
            this.numero = numero;
        }
        public String getEmision() {
            return emision;
        }
        public void setEmision(String emision) {
            this.emision = emision;
        }
        public String getFechaCorte() {
            return fechaCorte;
        }
        public void setFechaCorte(String fechaCorte) {
            this.fechaCorte = fechaCorte;
        }
        public String getFechaLimite() {
            return fechaLimite;
        }
        public void setFechaLimite(String fechaLimite) {
            this.fechaLimite = fechaLimite;
        }

        public Resolucion getResolucion() {
            return resolucion;
        }
        public void setResolucion(Resolucion resolucion) {
            this.resolucion = resolucion;
        }
        public String getSerializable() {
            return serializable;
        }
        public void setSerializable(String serializable) {
            this.serializable = serializable;
        }
        public String getOrigen() {
            return origen;
        }
        public void setOrigen(String origen) {
            this.origen = origen;
        }

        
    }
    public static class Cliente{
        private String documento;
        private String nombres;
        private String direccion;
        private String telefonos;
        private String email;
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
        public String getDireccion() {
            return direccion;
        }
        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }
        public String getTelefonos() {
            return telefonos;
        }
        public void setTelefonos(String telefonos) {
            this.telefonos = telefonos;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        
    }
    public static class Deudas{
        private String servicio;
        private String type;
        private String direccion;
        private Double base;
        private Float iva;
        private Float parcial;
        private Double total;
        
        public String getServicio() {
            return servicio;
        }
        public void setServicio(String servicio) {
            this.servicio = servicio;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public Double getBase() {
            return base;
        }
        public void setBase(Double base) {
            this.base = base;
        }
        public Float getIva() {
            return iva;
        }
        public void setIva(Float iva) {
            this.iva = iva;
        }
        public Float getParcial() {
            return parcial;
        }
        public void setParcial(Float parcial) {
            this.parcial = parcial;
        }
        public Double getTotal() {
            return total;
        }
        public void setTotal(Double total) {
            this.total = total;
        }
        public String getDireccion() {
            return direccion;
        }
        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }
        
    }

    public static class Empresa{
        private String nombre;
        private String nit;
        private String indicativo;
        private String url;
        private String direccion;
        private String telefono;
        public String getNombre() {
            return nombre;
        }
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        public String getNit() {
            return nit;
        }
        public void setNit(String nit) {
            this.nit = nit;
        }
        public String getIndicativo() {
            return indicativo;
        }
        public void setIndicativo(String indicativo) {
            this.indicativo = indicativo;
        }
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
        public String getDireccion() {
            return direccion;
        }
        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }
        public String getTelefono() {
            return telefono;
        }
        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }
        

        
    }

    public static class Resolucion{
        private String resolucion;
        private String rangoinicial;
        private String rangofinal;
        private String vigencia;
        private String fecha;
        public String getFecha() {
            return fecha;
        }
        public void setFecha(String fecha) {
            this.fecha = fecha;
        }
        private String prefijo;
        public String getResolucion() {
            return resolucion;
        }
        public void setResolucion(String resolucion) {
            this.resolucion = resolucion;
        }
        public String getRangoinicial() {
            return rangoinicial;
        }
        public void setRangoinicial(String rangoinicial) {
            this.rangoinicial = rangoinicial;
        }
        public String getRangofinal() {
            return rangofinal;
        }
        public void setRangofinal(String rangofinal) {
            this.rangofinal = rangofinal;
        }
        public String getVigencia() {
            return vigencia;
        }
        public void setVigencia(String vigencia) {
            this.vigencia = vigencia;
        }
        public String getPrefijo() {
            return prefijo;
        }
        public void setPrefijo(String prefijo) {
            this.prefijo = prefijo;
        }

        
    }

    public Double getTotalMes() {
        return totalMes;
    }

    public void setTotalMes(Double totalMes) {
        this.totalMes = totalMes;
    }
}
