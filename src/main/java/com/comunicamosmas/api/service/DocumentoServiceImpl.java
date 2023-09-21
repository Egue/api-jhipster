package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Admin;
import com.comunicamosmas.api.domain.Documento;
import com.comunicamosmas.api.repository.IAdminDao;
import com.comunicamosmas.api.repository.IDocumentoDao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentoServiceImpl implements IDocumentoService {

    @Autowired 
    IDocumentoDao documentoDao;

    @Override
    public List<Documento> findAll() {
        // TODO Auto-generated method stub
         return (List<Documento>) documentoDao.findAll();
    }

    @Override
    public Documento save(Documento documento) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public Documento findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }
 
}
