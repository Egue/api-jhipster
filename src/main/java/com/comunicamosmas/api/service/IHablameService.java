package com.comunicamosmas.api.service;

import com.comunicamosmas.api.web.rest.vm.ClassHablame;
import com.comunicamosmas.api.web.rest.vm.ClassResponseHablame;
import java.util.List;

public interface IHablameService {
    public ClassResponseHablame msmPriority(ClassHablame classHablame);
}
