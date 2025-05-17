package com.hjsolutions.isp_api.repository
import com.hjsolutions.isp_api.domain.Iptv
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository
import org.bson.types.ObjectId;

@Repository
interface IptvRepository: MongoRepository<Iptv, ObjectId> {
     
}