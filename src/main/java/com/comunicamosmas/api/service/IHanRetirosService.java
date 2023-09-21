package com.comunicamosmas.api.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.comunicamosmas.api.domain.HanRetiros;
import com.comunicamosmas.api.service.dto.ChartDataLineDTO;
import com.comunicamosmas.api.service.dto.HanRetirosCommentsDTO;
import com.comunicamosmas.api.service.dto.HanRetirosDTO;
import com.comunicamosmas.api.service.dto.ReporteHanRetirosDTO;

public interface IHanRetirosService {


    public HanRetiros save(HanRetiros retiros);

    public HanRetiros findById(Long id);

    public void deleteById(Long id);

    public List<HanRetiros> findAll();

    public List<HanRetirosDTO> findByIdContrato(Long idContrato);

    public List<HanRetirosCommentsDTO> messageRetiros(Integer idPadre);

    public void uploadImg(MultipartFile file , HanRetiros retiros);

    public void changeCerrado(Long id);

    public ChartDataLineDTO chartLine(Integer ano , List<Integer> servicios);

    public List<ReporteHanRetirosDTO> reporteHanRetiros(List<Integer>servicios , String estado);

    public void sendMailRetiros(Long id , List<String> email);

    
    
}
