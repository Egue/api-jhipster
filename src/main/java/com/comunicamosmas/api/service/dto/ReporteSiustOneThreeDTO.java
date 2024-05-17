package com.comunicamosmas.api.service.dto;

public class ReporteSiustOneThreeDTO {
    /*headerRow.createCell(0).setCellValue("Servicio");
                headerRow.createCell(1).setCellValue("Tipo Cliente");
                headerRow.createCell(2).setCellValue("Deuda");
                headerRow.createCell(3).setCellValue("Contrato");
                headerRow.createCell(4).setCellValue("Factura");
                headerRow.createCell(5).setCellValue("Base");
                headerRow.createCell(6).setCellValue("IVA");
                headerRow.createCell(7).setCellValue("# Tarifa");
                headerRow.createCell(8).setCellValue("Estrato");
                headerRow.createCell(9).setCellValue("Velocidad");
                headerRow.createCell(10).setCellValue("Tecnologia");
                headerRow.createCell(11).setCellValue("Concepto");
                headerRow.createCell(12).setCellValue("NC Base");
                headerRow.createCell(13).setCellValue("NC IVA"); */
                private String servicio;

                private String tipoCliente;

                private Integer deuda;

                private Integer contrato;

                private Integer factura;

                private Double base;

                private Float iva;

                private Integer tarifa;

                private Integer estrato;

                private Integer velocidad;

                private Integer tecnologia;

                private String concepto;

                private Double ncBase;

                private Double ncIva;

                public String getServicio() {
                    return servicio;
                }

                public void setServicio(String servicio) {
                    this.servicio = servicio;
                }

                public String getTipoCliente() {
                    return tipoCliente;
                }

                public void setTipoCliente(String tipoCliente) {
                    this.tipoCliente = tipoCliente;
                }

                public Integer getDeuda() {
                    return deuda;
                }

                public void setDeuda(Integer deuda) {
                    this.deuda = deuda;
                }

                public Integer getContrato() {
                    return contrato;
                }

                public void setContrato(Integer contrato) {
                    this.contrato = contrato;
                }

                public Integer getFactura() {
                    return factura;
                }

                public void setFactura(Integer factura) {
                    this.factura = factura;
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

                public Integer getTarifa() {
                    return tarifa;
                }

                public void setTarifa(Integer tarifa) {
                    this.tarifa = tarifa;
                }

                public Integer getEstrato() {
                    return estrato;
                }

                public void setEstrato(Integer estrato) {
                    this.estrato = estrato;
                }

                public Integer getVelocidad() {
                    return velocidad;
                }

                public void setVelocidad(Integer velocidad) {
                    this.velocidad = velocidad;
                }

                public Integer getTecnologia() {
                    return tecnologia;
                }

                public void setTecnologia(Integer tecnologia) {
                    this.tecnologia = tecnologia;
                }

                public String getConcepto() {
                    return concepto;
                }

                public void setConcepto(String concepto) {
                    this.concepto = concepto;
                }

                public Double getNcBase() {
                    return ncBase;
                }

                public void setNcBase(Double ncBase) {
                    this.ncBase = ncBase;
                }

                public Double getNcIva() {
                    return ncIva;
                }

                public void setNcIva(Double ncIva) {
                    this.ncIva = ncIva;
                }

                 

                
    
}
