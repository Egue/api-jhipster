package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.WinmaxPass;
import java.util.List;

public interface IWinmaxPassService {
    //listar todos
    public List<WinmaxPass> findAll();

    //guardar
    public WinmaxPass save(WinmaxPass winmaxPass);

    //eliminar
    public void deleteById(Long id);

    //buscar por id
    public WinmaxPass findById(Long id);

    public Long countFindByIdContrato(Long idContrato);

    public WinmaxPass findByIdContrato(Long idContrato);
}
