package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.WinmaxPass;
import com.comunicamosmas.api.repository.IWinmaxPassDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WinmaxPassServiceImpl implements IWinmaxPassService {

    @Autowired
    IWinmaxPassDao winmaxDao;

    @Override
    public List<WinmaxPass> findAll() {
        // TODO Auto-generated method stub
        return (List<WinmaxPass>) winmaxDao.findAll();
    }

    @Override
    public WinmaxPass save(WinmaxPass winmaxPass) {
        return winmaxDao.save(winmaxPass);
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
        winmaxDao.deleteById(id);
    }

    @Override
    public WinmaxPass findById(Long id) {
        // TODO Auto-generated method stub
        return winmaxDao.findById(id).orElse(null);
    }

    @Override
    public Long countFindByIdContrato(Long idContrato) {
        // TODO Auto-generated method stub
        return winmaxDao.countFindByIdContrato(idContrato);
    }

    @Override
    public WinmaxPass findByIdContrato(Long idContrato) {
        // TODO Auto-generated method stub
        return winmaxDao.findByIdContrato(idContrato);
    }
}
