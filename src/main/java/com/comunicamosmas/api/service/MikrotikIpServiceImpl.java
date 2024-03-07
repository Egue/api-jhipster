package com.comunicamosmas.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.Estacion;
import com.comunicamosmas.api.domain.MikrotikIp; 
import com.comunicamosmas.api.repository.IMikrotikIpDao;
import com.comunicamosmas.api.service.dto.ClassErrorDTO;
import com.comunicamosmas.api.service.dto.MikrotikQueueSimplePadreDTO;
import com.comunicamosmas.api.service.dto.SegmentoDTO;
import com.comunicamosmas.api.service.dto.SegmentoIPDTO;
import com.comunicamosmas.api.service.dto.SegmentoIPDTO.Pool; 

@Service
public class MikrotikIpServiceImpl implements IMikrotikIpService {

    @Autowired
    IMikrotikIpDao mikrotikIpDao;

    @Autowired
    IEstacionService estacionService;

    @Autowired
    IApiRBMikrotikService apiRBMikrotikService;

    @Autowired
    IGlobalFunctionsService globalFunctionsService;
 

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
        int tercerOcteto = Integer.parseInt(ipSplit[2]);
        //
        for (int j = tercerOcteto ; j < (octeto + tercerOcteto); j++) {
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
    public MikrotikIp updatedStatus(SegmentoDTO segmento) {

        //MikrotikIp findIp = mikrotikIpDao.findById(id).orElse(null);
        MikrotikIp ip = new MikrotikIp();
        if(segmento.getId() != 0)
        {
            ip.setId(segmento.getId());            
        } 

        ip.setIp(segmento.getIp());
        ip.setEstado(1L);
        ip.setIdSegmentoIp(segmento.getIdSegmentoIp());
         

        return mikrotikIpDao.save(ip);
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

    @SuppressWarnings("unchecked")
    @Override
    public List<MikrotikIp>  findByIdPool(Long idPool) {
         

        Optional<List<Object[]>> result = mikrotikIpDao.findByIdPool(idPool);
        Pool name = new Pool();
        SegmentoIPDTO segmento = new SegmentoIPDTO(); 
        result.ifPresent(resp -> {
            for(Object[] rs : resp)
            {
                name.setName((String) rs[2]);
                long stationId = (long) (int) rs[1];
                segmento.setIdEstacion(stationId);
                long idSegmento = (long) (int) rs[0];
                segmento.setIdPool(idSegmento);
            }

        });
        if(result.isEmpty())
        {
            return new ArrayList<>();
        }
        //consultar ip disponibles 
        List<MikrotikIp> mikrotikIp = this.findAllBySegmentoIp(segmento.getIdPool()); //es el id_segmento solo q se guarda en el idPool
        //realizamos un filtro de disponibles
        //si esta vacio continuar con el range

        List<MikrotikIp> mikrotikIpActive = mikrotikIp.stream()
                                    .filter(ip -> ip.getEstado() == 0)
                                    .collect(Collectors.toList());
        //validamos si hay disponibles
        if(!mikrotikIpActive.isEmpty())
        {
            List<MikrotikIp> listActive = new ArrayList<>();
            listActive.add(mikrotikIpActive.get(0));
            return  listActive;
        }
        //filtramos las usadas
        List<MikrotikIp> mikrotikUsadas = mikrotikIp.stream()
                                    .filter(ip -> ip.getEstado() == 1)
                                    .collect(Collectors.toList());
         
        String command = "/ip/pool/print where name="+name.getName();

        Estacion estacion = estacionService.findById(segmento.getIdEstacion());

        List<Map<String,String>> getPool = apiRBMikrotikService.listSendCommand(command, estacion);

        String range = null;
        
        if(getPool != null)
        {
            for(Map<String, String> rs : getPool )
            {
                range = rs.get("ranges");
            }
        }

        String [] rangePart = range.split("\\,");
        List<String> ips = new ArrayList<>();
        for(String rang : rangePart)
        {
            String[] guion = rang.split("\\-");
            ips = globalFunctionsService.generateIPRange(guion[0], guion[1]);
        }
        //validamos si 
        if(mikrotikUsadas.isEmpty())
        {
            String oneIp = ips.get(0);

            List<MikrotikIp> oneMikrotikIp = new ArrayList<>();
            MikrotikIp obj = new MikrotikIp();
            obj.setEstado(0L);
            obj.setIp(oneIp);
            obj.setIdSegmentoIp(segmento.getIdPool()); 

            oneMikrotikIp.add(obj);

            return oneMikrotikIp;
        }else{

            List<String> finalList = new ArrayList<>(ips);

            for(MikrotikIp active: mikrotikUsadas)
            {
                    finalList.remove(active.getIp());
            }
            String oneIp = finalList.get(0);
            List<MikrotikIp> oneMikrotikIp = new ArrayList<>();
            MikrotikIp obj = new MikrotikIp();
            obj.setEstado(0L);
            obj.setIp(oneIp);
            obj.setIdSegmentoIp(segmento.getIdPool()); 

            oneMikrotikIp.add(obj);

            return oneMikrotikIp;

        }



    }
}
