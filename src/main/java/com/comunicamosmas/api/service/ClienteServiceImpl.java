package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Cliente;
import com.comunicamosmas.api.repository.IClienteDao;
import com.comunicamosmas.api.service.dto.ClienteDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    IClienteDao clienteDao;

    @Override
    public List<Cliente> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Cliente save(Cliente cliente) {
        if(cliente.getApellidosRep() == null) 
        {
            cliente.setApellidosRep(" ");
            
        }
        if( cliente.getNombresRep() == null)
        {
            cliente.setNombresRep(" ");
        }
        if(cliente.getNombreComercial() == null)
        {
            cliente.setNombreComercial(" ");
        }
        if(cliente.getRazonSocial() == null)
        {
            cliente.setRazonSocial(" ");
        }
        if(cliente.getTelefono() == null)
        {
            cliente.setTelefono(0L);
        }
        // TODO Auto-generated method stub
        cliente.setAutorizaSms(1L);
        cliente.setBomberil(1L);
        cliente.setEstadoCivil(" ");
        cliente.setEstrato(0);
        cliente.setfNacimiento(0);
        cliente.setIdOperaA(1L);
        cliente.setIdOperaB(1L);
        cliente.setObservaciones(" ");
        cliente.setSha(" ");
        cliente.setTipoVivienda(" ");
        return clienteDao.save(cliente);
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Cliente findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Cliente getClientByIdContrato(Long idContrato) {
        return clienteDao.getClientByIdContrato(idContrato);
    }

    @Override
    public List<Cliente> findByDocumento(String documento) {
        // TODO Auto-generated method stub
        return (List<Cliente>) clienteDao.findByDocumento(documento);
    }

    @Override
    public List<ClienteDTO> findByName(String nombre) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'findByName'");
        Optional<List<Object[]>> result = clienteDao.findByName(nombre);

        List<ClienteDTO> clientes = result.map(resp -> {

            List<ClienteDTO> clie = new ArrayList<>();

            for (Object[] rs : resp) {
                ClienteDTO obj = new ClienteDTO();
                if (rs[1].equals("N")) {
                    String concat = rs[3] + " " + rs[4] + " " + rs[5] + " " + rs[6] + " | " + rs[7].toString();
                    obj.setId((Integer) rs[0]);
                    obj.setConcatenar(concat);
                } else {

                    obj.setId((Integer) rs[0]);
                    obj.setConcatenar((String) rs[2]);
                }

                clie.add(obj);
            }

            return clie;

        }).orElse(null);

        return clientes;

    }

    @Override
    public List<ClienteDTO> findByCus(Long cus) {
        // TODO Auto-generated method stub
         Optional<List<Object[]>> result = clienteDao.findByCus(cus);

        List<ClienteDTO> clientes = result.map(resp -> {

            List<ClienteDTO> clie = new ArrayList<>();

            for (Object[] rs : resp) {
                ClienteDTO obj = new ClienteDTO();
                if (rs[1].equals("N")) {
                    String concat = rs[3] + " " + rs[4] + " " + rs[5] + " " + rs[6] + " | " + rs[7].toString();
                    obj.setId((Integer) rs[0]);
                    obj.setConcatenar(concat);
                } else {

                    obj.setId((Integer) rs[0]);
                    obj.setConcatenar((String) rs[2]);
                }

                clie.add(obj);
            }

            return clie;

        }).orElse(null);

        return clientes;
    }

    @Override
    public List<ClienteDTO> validExisteCliente(String documento) {
        // TODO Auto-generated method stub
        Optional<List<Object[]>> result = clienteDao.validExiste(documento);

        List<ClienteDTO> cliente = result.map(resp->{

            List<ClienteDTO> cli = new ArrayList<>();
            for(Object[] rs : resp)
            {   ClienteDTO obj = new ClienteDTO();

                obj.setId((Integer) rs[0]);
                cli.add(obj);
            }

            return cli;

        }).orElse(null);

        return cliente;
    }
}
