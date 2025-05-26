package com.hjsolutions.isp_api.repository 

import com.hjsolutions.isp_api.domain.Inventory
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository
import org.bson.types.ObjectId;

@Repository
interface InventoryRepository: MongoRepository<Inventory, ObjectId> {

    fun findByHostname(hostname: String): Inventory?

    fun findBySerialNumber(serialNumber: String): Inventory?

    fun findByLocation(location: String): Inventory?

    fun findByAsignedTo(asignedTo: String): Inventory?
     
}