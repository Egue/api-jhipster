package com.comunicamosmas.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.Cliente;
import com.comunicamosmas.api.repository.IClienteDao;
import com.comunicamosmas.api.service.dto.MigrationDBDTO.ClienteDTO;

@Service
public class MigrationDBServiceImpl {

    private final IClienteDao clienteDao;
    public MigrationDBServiceImpl(IClienteDao clienteDao){
        this.clienteDao = clienteDao;
    }
    
    public void create_cliente(List<ClienteDTO> clientes){
        List<Cliente> cliente = new ArrayList<>();
        clientes.forEach(item -> {
           Cliente cli = new Cliente();
           String tipoCliente = (item.getTipo_identificacion() == "NI") ? "J" : "N";
           cli.setTipoCliente(tipoCliente);
           cli.setRazonSocial(item.getNombre()+" "+item.getApellidos());
           cli.setApellidoPaterno(item.getApellidos());
           cli.setNombrePrimer(item.getNombre());
           cli.setGenero(item.getSexo());
           cli.setNombresRep(item.getNombre_representante());
           cli.setApellidosRep(" ");
           Integer tipoDocument = item.getTipo_identificacion() == "NI" ? 2 : 1;
           cli.setIdDocumento(tipoDocument);
           cli.setDocumento(Long.valueOf(item.getIdentificacion()));
           cli.setDv(Long.valueOf(item.getDv()));
           cli.setfNacimiento(19000000);
           cli.setEstadoCivil("1");
           cli.setTipoVivienda("1");
           cli.setTelefono(Long.valueOf(item.getTelefono1()));
           cli.setCelularA(item.getTelefono1());
           cli.setIdOperaA(1L);
           cli.setCelularB(item.getTelefono2());
           cli.setIdOperaB(1L);
           cli.setMail(item.getEmail());
           cli.setEstrato(0);
           cli.setObservaciones("migrado id= " + item.getId());
            cli.setSha(" ");
            cli.setBomberil(0L);
            cli.setAutorizaSms(1L);
            cli.setPortalweb("");
            cliente.add(cli);
        });

        try {
            clienteDao.saveAll(cliente);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
