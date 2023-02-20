package com.comunicamosmas.api.service;

import com.comunicamosmas.api.service.dto.ClassHablameDTO;
import com.comunicamosmas.api.service.dto.ClassResponseHablameDTO;  

public interface IHablameService {
    public ClassResponseHablameDTO msmPriority(ClassHablameDTO classHablame);
}
