package com.comunicamosmas.api.service.dto;
import com.comunicamosmas.api.service.mapper.EmptyStringToZeroConverter;
import com.comunicamosmas.api.service.mapper.EmptyStringToZeroConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvNumber;

public class MigrationDBDTO {

    private ClienteDTO clienteDTO;

     public ClienteDTO getClienteDTO() {
        return clienteDTO;
    }
 
    public void setClienteDTO(ClienteDTO clienteDTO) {
        this.clienteDTO = clienteDTO;
    }
 
    public static class ClienteDTO{
    
    @CsvCustomBindByName(column = "ID", converter = EmptyStringToZeroConverter.class)
    @CsvNumber(value="0")
    private Integer id;
    @CsvCustomBindByName(column = "IDENTIFICACION", converter = EmptyStringToZeroConverter.class)
    private String identificacion;
    @CsvCustomBindByName(column = "TIPO_IDENTIFICACION", converter = EmptyStringToZeroConverter.class)
    private String tipo_identificacion ;
    @CsvCustomBindByName(column = "DV", converter = EmptyStringToZeroConverter.class)
    private String dv ;
    @CsvCustomBindByName(column = "SEXO", converter = EmptyStringToZeroConverter.class)
    private String sexo ;
    @CsvCustomBindByName(column = "ESTADO_CIVIL", converter = EmptyStringToZeroConverter.class)
    private String estado_civil ;
    @CsvCustomBindByName(column = "COD_MUNICIPIO_EXPEDICION_CEDULA", converter = EmptyStringToZeroConverter.class)
    @CsvNumber(value="0")
    private Integer cod_municipio ;
    @CsvCustomBindByName(column = "FECHA_EXPEDICION_CEDULA", converter = EmptyStringToZeroConverter.class)
    private String fecha_expedicion_cedula ;
    @CsvCustomBindByName(column = "NOMBRE", converter = EmptyStringToZeroConverter.class)
    private String nombre ;
    @CsvCustomBindByName(column = "APELLIDOS", converter = EmptyStringToZeroConverter.class)
    private String apellidos  ;
    @CsvCustomBindByName(column = "FECHA_NACIMIENTO", converter = EmptyStringToZeroConverter.class)
    private String fecha_nacimiento ;
    @CsvCustomBindByName(column = "COD_MUNICIPIO_DOMICILIO", converter = EmptyStringToZeroConverter.class)
    @CsvNumber(value="0")
    private Integer cod_municipio_domicilio ;
    @CsvCustomBindByName(column = "COD_BARRIO_DOMICILIO", converter = EmptyStringToZeroConverter.class)
    @CsvNumber(value="0")
    private Integer cod_barrio_domicilio  ;
    @CsvCustomBindByName(column = "DIRECCION_DOMICILIO", converter = EmptyStringToZeroConverter.class)
    private String direccion_domicilio ;
    @CsvCustomBindByName(column = "PUNTO_REFERENCIA_DOMICILIO", converter = EmptyStringToZeroConverter.class)
    private String punto_referencia_domicilio ;
    @CsvCustomBindByName(column = "INDICATIVO1", converter = EmptyStringToZeroConverter.class)
    private String indicativo1 ;
    @CsvCustomBindByName(column = "TELEFONO1", converter = EmptyStringToZeroConverter.class)
    private String telefono1 ;
    @CsvCustomBindByName(column = "EXTENCION1", converter = EmptyStringToZeroConverter.class)
    private String extension1 ;
    @CsvCustomBindByName(column = "INDICATIVO2", converter = EmptyStringToZeroConverter.class)
    private String indicativo2 ;
    @CsvCustomBindByName(column = "TELEFONO2", converter = EmptyStringToZeroConverter.class)
    private String telefono2 ;
    @CsvCustomBindByName(column = "EXTENCION2", converter = EmptyStringToZeroConverter.class)
    private String extension2 ;
    @CsvCustomBindByName(column = "MOVIL", converter = EmptyStringToZeroConverter.class)
    private String movil   ;
    @CsvCustomBindByName(column = "EMAIL", converter = EmptyStringToZeroConverter.class)
    private String email ;
    @CsvCustomBindByName(column = "ACTIVIDAD_ECONOMICA", converter = EmptyStringToZeroConverter.class)
    private String actividad_economica ;
    @CsvCustomBindByName(column = "EMPRESA", converter = EmptyStringToZeroConverter.class)
    private String empresa ;
    @CsvCustomBindByName(column = "COD_MUNICIPIO_EMPRESA", converter = EmptyStringToZeroConverter.class)
    @CsvNumber(value="0")
    private Integer cod_municipio_empresa ;
    @CsvCustomBindByName(column = "COD_BARRIO_EMPRESA", converter = EmptyStringToZeroConverter.class)
    @CsvNumber(value="0")
    private Integer cod_barrio_empresa ;
    @CsvCustomBindByName(column = "DIRECCION_EMPRESA", converter = EmptyStringToZeroConverter.class)
    private String cod_direccion_empresa ;
    @CsvCustomBindByName(column = "INDICATIVO_EMPRESA", converter = EmptyStringToZeroConverter.class)
    private String indicativo_empresa ;
    @CsvCustomBindByName(column = "TELEFONO_EMPRESA", converter = EmptyStringToZeroConverter.class)
    private String telefono_empresa ;
    @CsvCustomBindByName(column = "EXTENCION_EMPRESA", converter = EmptyStringToZeroConverter.class)
    private String extension_empresa ;
    @CsvCustomBindByName(column = "NOMBRE_COMPLETO_REFERENCIA", converter = EmptyStringToZeroConverter.class)
    private String nombre_completo_referencia ;
    @CsvCustomBindByName(column = "COD_MUNICIPIO_REFERENCIA", converter = EmptyStringToZeroConverter.class)
    @CsvNumber(value="0")
    private String cod_municipio_referencia  ;
    @CsvCustomBindByName(column = "COD_BARRIO_REFERENCIA" ,converter = EmptyStringToZeroConverter.class)
    @CsvNumber(value="0")
    private String cod_barrio_referencia  ;
    @CsvCustomBindByName(column = "DIRECCION_REFERENCIA", converter = EmptyStringToZeroConverter.class)
    private String direccion_referencia  ;
    @CsvCustomBindByName(column = "INDICATIVO_REFERENCIA", converter = EmptyStringToZeroConverter.class)
    private String indicativo_referencia  ;
    @CsvCustomBindByName(column = "TELEFONO_REFERENCIA", converter = EmptyStringToZeroConverter.class)
    private String telefono_referencia  ;
    @CsvCustomBindByName(column = "EXTENCION_REFERENCIA", converter = EmptyStringToZeroConverter.class)
    private String extension_referencia  ;
    @CsvCustomBindByName(column = "MOVIL_REFERENCIA", converter = EmptyStringToZeroConverter.class)
    private String movil_referencia  ;
    @CsvCustomBindByName(column = "MOVIL_TRASLADO", converter = EmptyStringToZeroConverter.class)
    private String movil_traslado  ;
    @CsvCustomBindByName(column = "DIGITO_VERIFICACION", converter = EmptyStringToZeroConverter.class)
    private String digito_verificacion  ;
    @CsvCustomBindByName(column = "NOMBRE_REPRESENTANTE", converter = EmptyStringToZeroConverter.class)
    private String nombre_representante  ;
    @CsvCustomBindByName(column = "IDENTIFICACION_REPRESENTANTE", converter = EmptyStringToZeroConverter.class)
    private String identificacion_representante  ;
    @CsvCustomBindByName(column = "FECHA", converter = EmptyStringToZeroConverter.class)
    private String fecha;

    public Integer getId()
    {
        return id;
    }
    public void setId(Integer id)
    {
        this.id = id;
    }
    public String getIdentificacion() {
        return identificacion;
    }
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    public String getTipo_identificacion() {
        return tipo_identificacion;
    }
    public void setTipo_identificacion(String tipo_identificacion) {
        this.tipo_identificacion = tipo_identificacion;
    }
    public String getDv() {
        return dv;
    }
    public void setDv(String dv) {
        this.dv = dv;
    }
    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    public String getEstado_civil() {
        return estado_civil;
    }
    public void setEstado_civil(String estado_civil) {
        this.estado_civil = estado_civil;
    }
    public Integer getCod_municipio() {
        return cod_municipio;
    }
    public void setCod_municipio(Integer cod_municipio) {
        this.cod_municipio = cod_municipio;
    }
    public String getFecha_expedicion_cedula() {
        return fecha_expedicion_cedula;
    }
    public void setFecha_expedicion_cedula(String fecha_expedicion_cedula) {
        this.fecha_expedicion_cedula = fecha_expedicion_cedula;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }
    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }
    public Integer getCod_municipio_domicilio() {
        return cod_municipio_domicilio;
    }
    public void setCod_municipio_domicilio(Integer cod_municipio_domicilio) {
        this.cod_municipio_domicilio = cod_municipio_domicilio;
    }
     
    public String getDireccion_domicilio() {
        return direccion_domicilio;
    }
    public void setDireccion_domicilio(String direccion_domicilio) {
        this.direccion_domicilio = direccion_domicilio;
    }
    public String getPunto_referencia_domicilio() {
        return punto_referencia_domicilio;
    }
    public void setPunto_referencia_domicilio(String punto_referencia_domicilio) {
        this.punto_referencia_domicilio = punto_referencia_domicilio;
    }
    public String getIndicativo1() {
        return indicativo1;
    }
    public void setIndicativo1(String indicativo1) {
        this.indicativo1 = indicativo1;
    }
    public String getTelefono1() {
        return telefono1;
    }
    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }
    public String getExtension1() {
        return extension1;
    }
    public void setExtension1(String extension1) {
        this.extension1 = extension1;
    }
    public String getIndicativo2() {
        return indicativo2;
    }
    public void setIndicativo2(String indicativo2) {
        this.indicativo2 = indicativo2;
    }
    public String getTelefono2() {
        return telefono2;
    }
    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }
    public String getExtension2() {
        return extension2;
    }
    public void setExtension2(String extension2) {
        this.extension2 = extension2;
    }
    public String getMovil() {
        return movil;
    }
    public void setMovil(String movil) {
        this.movil = movil;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getActividad_economica() {
        return actividad_economica;
    }
    public void setActividad_economica(String actividad_economica) {
        this.actividad_economica = actividad_economica;
    }
    public String getEmpresa() {
        return empresa;
    }
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
    public Integer getCod_municipio_empresa() {
        return cod_municipio_empresa;
    }
    public void setCod_municipio_empresa(Integer cod_municipio_empresa) {
        this.cod_municipio_empresa = cod_municipio_empresa;
    }
    public Integer getCod_barrio_empresa() {
        return cod_barrio_empresa;
    }
    public void setCod_barrio_empresa(Integer cod_barrio_empresa) {
        this.cod_barrio_empresa = cod_barrio_empresa;
    }
    public String getCod_direccion_empresa() {
        return cod_direccion_empresa;
    }
    public void setCod_direccion_empresa(String cod_direccion_empresa) {
        this.cod_direccion_empresa = cod_direccion_empresa;
    }
    public String getIndicativo_empresa() {
        return indicativo_empresa;
    }
    public void setIndicativo_empresa(String indicativo_empresa) {
        this.indicativo_empresa = indicativo_empresa;
    }
    public String getTelefono_empresa() {
        return telefono_empresa;
    }
    public void setTelefono_empresa(String telefono_empresa) {
        this.telefono_empresa = telefono_empresa;
    }
    public String getExtension_empresa() {
        return extension_empresa;
    }
    public void setExtension_empresa(String extension_empresa) {
        this.extension_empresa = extension_empresa;
    }
    public String getNombre_completo_referencia() {
        return nombre_completo_referencia;
    }
    public void setNombre_completo_referencia(String nombre_completo_referencia) {
        this.nombre_completo_referencia = nombre_completo_referencia;
    }
    public String getCod_municipio_referencia() {
        return cod_municipio_referencia;
    }
    public void setCod_municipio_referencia(String cod_municipio_referencia) {
        this.cod_municipio_referencia = cod_municipio_referencia;
    }
    public String getCod_barrio_referencia() {
        return cod_barrio_referencia;
    }
    public void setCod_barrio_referencia(String cod_barrio_referencia) {
        this.cod_barrio_referencia = cod_barrio_referencia;
    }
    public String getDireccion_referencia() {
        return direccion_referencia;
    }
    public void setDireccion_referencia(String direccion_referencia) {
        this.direccion_referencia = direccion_referencia;
    }
    public String getIndicativo_referencia() {
        return indicativo_referencia;
    }
    public void setIndicativo_referencia(String indicativo_referencia) {
        this.indicativo_referencia = indicativo_referencia;
    }
    public String getTelefono_referencia() {
        return telefono_referencia;
    }
    public void setTelefono_referencia(String telefono_referencia) {
        this.telefono_referencia = telefono_referencia;
    }
    public String getExtension_referencia() {
        return extension_referencia;
    }
    public void setExtension_referencia(String extension_referencia) {
        this.extension_referencia = extension_referencia;
    }
    public String getMovil_referencia() {
        return movil_referencia;
    }
    public void setMovil_referencia(String movil_referencia) {
        this.movil_referencia = movil_referencia;
    }
    public String getMovil_traslado() {
        return movil_traslado;
    }
    public void setMovil_traslado(String movil_traslado) {
        this.movil_traslado = movil_traslado;
    }
    public String getDigito_verificacion() {
        return digito_verificacion;
    }
    public void setDigito_verificacion(String digito_verificacion) {
        this.digito_verificacion = digito_verificacion;
    }
    public String getNombre_representante() {
        return nombre_representante;
    }
    public void setNombre_representante(String nombre_representante) {
        this.nombre_representante = nombre_representante;
    }
    public String getIdentificacion_representante() {
        return identificacion_representante;
    }
    public void setIdentificacion_representante(String identificacion_representante) {
        this.identificacion_representante = identificacion_representante;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    
    }



   
}
