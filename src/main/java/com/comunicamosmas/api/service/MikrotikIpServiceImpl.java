package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.MikrotikIp;
import com.comunicamosmas.api.repository.IMikrotikIpDao;
import com.comunicamosmas.api.service.dto.ClassErrorDTO; 
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MikrotikIpServiceImpl implements IMikrotikIpService {

    @Autowired
    IMikrotikIpDao mikrotikIpDao;

    @Override
    public void save(MikrotikIp mikrotikIp) {
        mikrotikIpDao.save(mikrotikIp);
    }

    @Override
    public List<MikrotikIp> findAllBySegmentoIp(Long idSegmentoIp) {
        return (List<MikrotikIp>) mikrotikIpDao.findAllBySegmentoIp(idSegmentoIp);
    }

    @Override
    public void recorrer(String[] segmento, Long id) {
        List<MikrotikIp> listMikrotikIp = new ArrayList<MikrotikIp>();
        //asigna a ip = 10.250.0.1
        String ip = segmento[0];
        //asigna a ip 23
        String rango = segmento[1];

        int limit = 0;
        int octeto = 1;
        switch (rango) {
            case "32":
                limit = 3; //1
                octeto = 1;
                break;
            case "31":
                limit = 4; //2
                octeto = 1;
                break;
            case "30":
                limit = 6; //4
                octeto = 1;
                break;
            case "29":
                limit = 10; //8
                octeto = 1;
                break;
            case "28":
                limit = 18; //16
                octeto = 1;
                break;
            case "27":
                limit = 34; //32
                octeto = 1;
                break;
            case "26":
                limit = 66; //64
                octeto = 1;
                break;
            case "25":
                limit = 130; //128
                octeto = 1;
                break;
            case "24":
                limit = 255; //256
                octeto = 1;
                break;
            case "23":
                limit = 255; //
                octeto = 2;
                break;
            case "22":
                limit = 255;
                octeto = 4;
                break;
            case "21":
                limit = 255;
                octeto = 8;
                break;
        }
        //separa 10.250.0.0
        String[] ipSplit = ip.split("\\.");
        //
        for (int j = 0; j < octeto; j++) {
            for (int i = 2; i < limit; i++) {
                String newIp = ipSplit[0] + "." + ipSplit[1] + "." + j + "." + i;
                MikrotikIp mikrotikIp = new MikrotikIp();
                mikrotikIp.setEstado(0L);
                mikrotikIp.setIdSegmentoIp(id);
                mikrotikIp.setIp(newIp);
                listMikrotikIp.add(mikrotikIp);
                //mikrotikIpDao.save(mikrotikIp);
                //System.out.print(newIp+"\n");

            }
        }

        mikrotikIpDao.saveAll(listMikrotikIp);
    }

    @Override
    public List<MikrotikIp> findAllBySegmentoIpStatus(Long idSegmentoIp) {
        return (List<MikrotikIp>) mikrotikIpDao.findAllBySegmentoIpStatus(idSegmentoIp);
    }

    @Override
    public MikrotikIp findById(Long id) {
        // TODO Auto-generated method stub
        return mikrotikIpDao.findById(id).orElse(null);
    }

    @Override
    public void updatedStatus(Long id) {
        MikrotikIp findIp = mikrotikIpDao.findById(id).orElse(null);

        findIp.setEstado(1L);

        mikrotikIpDao.save(findIp);
    }

    @Override
    public ClassErrorDTO deleteByIdSegmento(Long idSegmento) {
        //buscar por el id si existen activos
        int count = mikrotikIpDao.countActiveByIdSegmento(idSegmento);
        ClassErrorDTO error = new ClassErrorDTO();
        if (count > 0) {
            error.setError(true);
            error.setMsm("Existen ip usadas");
            return error;
        }
        try {
            List<MikrotikIp> result = mikrotikIpDao.findAllBySegmentoIp(idSegmento);

            for (MikrotikIp ip : result) {
                mikrotikIpDao.deleteById(ip.getId());
            }
        } catch (Exception e) {
            error.setError(true);
            error.setMsm(e.getMessage());

            return error;
        }
        error.setError(false);
        return error;
    }

    @Override
    public void deleteById(Long idIp) {
        mikrotikIpDao.deleteById(idIp);
    }

    @Override
    public void saveAll(List<MikrotikIp> mikrotikIp) {
        mikrotikIpDao.saveAll(mikrotikIp);
    }

    @Override
    public List<MikrotikIp> findByIdPool(Long idPool) {
        // TODO Auto-generated method stub
        return (List<MikrotikIp>) mikrotikIpDao.findByIdPool(idPool);
    }
}
