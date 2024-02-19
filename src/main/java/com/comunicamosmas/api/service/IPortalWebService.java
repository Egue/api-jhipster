package com.comunicamosmas.api.service;

import java.util.List;

import com.comunicamosmas.api.service.dto.AdminPortalWebDTO;
import com.comunicamosmas.api.service.dto.AdminUserDTO;
import com.comunicamosmas.api.service.dto.ClientePortalWebDTO;

public interface IPortalWebService {
    
    public void addUser(ClientePortalWebDTO clientePortalWebDTO ,  String token);

    public List<AdminUserDTO> userPortalWeb(String token);

    public void createUser(AdminPortalWebDTO user);

    public void activate(String token);

    public ClientePortalWebDTO findByDocumento(String documento , String token);

}
