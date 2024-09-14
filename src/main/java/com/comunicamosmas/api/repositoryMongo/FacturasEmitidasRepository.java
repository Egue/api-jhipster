package com.comunicamosmas.api.repositoryMongo;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.comunicamosmas.api.domainMongo.FacturasEmitidas;

public interface FacturasEmitidasRepository extends MongoRepository<FacturasEmitidas, ObjectId>{
    

    public Optional<List<FacturasEmitidas>> findByIdCampaign(Integer idCampaign);

    public Page<FacturasEmitidas> findByIdCliente(Integer idCliente , Pageable pageable);

    public FacturasEmitidas findByIdClienteAndFacturaAndIdCampaign(Integer idCliente , String factura , Integer IdCampaign);
}