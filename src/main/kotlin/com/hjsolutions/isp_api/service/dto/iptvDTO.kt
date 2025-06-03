package com.hjsolutions.isp_api.service.dto
import java.io.Serializable
import com.hjsolutions.isp_api.domain.Iptv
import org.bson.types.ObjectId;

data class IptvDTO(
    var id: ObjectId? = null,
    var name: String? = null, 
    var url: String? = null, 
    var urlimage: String? = null,
):Serializable {
    // Additional methods or annotations can be added here if needed
    constructor( iptv:Iptv): this(iptv.id, iptv.name, iptv.url ,iptv.urlimage)

    override fun toString(): String {
        return "IptvDTO(id=$id, name=$name,   url=$url)"
    }

    companion object {
         private const val serialVersionUID = 1L
    }
}