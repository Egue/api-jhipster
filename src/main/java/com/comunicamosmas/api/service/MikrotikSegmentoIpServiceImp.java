package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.MikrotikSegmentoIp;
import com.comunicamosmas.api.repository.IMikrotikSegmentoIpDao;
import com.comunicamosmas.api.service.dto.SegmentoWithPoolDTO; 
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MikrotikSegmentoIpServiceImp implements IMikrotikSegmentoIpService {

    @Autowired
    IMikrotikSegmentoIpDao mikrotikSegmentoIpDao;

    @Override
    public MikrotikSegmentoIp findById(Long id) {
        // TODO Auto-generated method stub
        return mikrotikSegmentoIpDao.findById(id).orElse(null);
    }

    @Override
    public List<MikrotikSegmentoIp> findByIdEstacion(Long id) {
        // TODO Auto-generated method stub
        return (List<MikrotikSegmentoIp>) mikrotikSegmentoIpDao.findByIdEstacion(id);
    }

    @Override
    public void save(MikrotikSegmentoIp mikrotikSegmentoIp) {
        mikrotikSegmentoIpDao.save(mikrotikSegmentoIp);
    }

    @Override
    public String[] splitSegment(MikrotikSegmentoIp mikrotikSegmentoIp) {
        String segmento = mikrotikSegmentoIp.getSegmento();
        String[] parts = segmento.split("/");

        return parts;
    }

    @Override
    public String[] validarSegmento(MikrotikSegmentoIp mikrotikSegmentoIp) {
        String ip = mikrotikSegmentoIp.getSegmento();

        String[] red = ip.split("/");

        String[] segmento = {};

        int limit = 0;

        int validar = Integer.parseInt(red[1]);
        //red[0] 10.252.0.0 , red[1] 32
        System.out.print(validar);
        if (validar >= 24) {
            segmento[0] = red[0];

            System.out.print("hola");
        }
        if (validar <= 23) {
            System.out.print("ey");
            switch (red[1]) {
                case "23":
                    limit = 2;
                    break;
                case "22":
                    limit = 4;
                    break;
                case "21":
                    limit = 8;
                    break;
                case "20":
                    limit = 16;
                    break;
            }

            //crear las ip
            String[] ipLetras = red[0].split("\\.");
            System.out.print(ipLetras[2] + "limit" + limit);
            int octeoTres = Integer.parseInt(ipLetras[2]);
            int contador = 0;
            for (int i = 1; i <= limit; i++) {
                segmento[contador] = ipLetras[0] + "." + ipLetras[1] + "." + octeoTres + "." + ipLetras[3];
                System.out.print(segmento[contador]);
                contador++;
                octeoTres++;
            }
        }

        return segmento;
    }

    @Override
    public int countFindByIdEstacionAndName(Long idEstacion, String segmento) {
        // TODO Auto-generated method stub
        return mikrotikSegmentoIpDao.countFindByIdEstacionAndName(idEstacion, segmento);
    }

    @Override
    public List<MikrotikSegmentoIp> findByIdPool(Long idPool) {
        // TODO Auto-generated method stub
        return (List<MikrotikSegmentoIp>) mikrotikSegmentoIpDao.findByIdPool(idPool);
    }

    @Override
    public void deleteById(Long id) {
        mikrotikSegmentoIpDao.deleteById(id);
    }

    @Override
    public int countDFindIpByPublic(String ipPublic, String segmento) {
        int count = mikrotikSegmentoIpDao.countDFindIpByPublic(ipPublic, segmento);

        return count;
    }

    @Override
    public List<SegmentoWithPoolDTO> findByidPoolAndEstado(Long idPool) {
        // TODO Auto-generated method stub
        return mikrotikSegmentoIpDao.findByidPoolAndEstado(idPool);
    }
}
