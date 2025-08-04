package com.hjsolutions.isp_api.service.dto
import java.io.Serializable
import com.hjsolutions.isp_api.domain.EquiposAsignados
import liquibase.pro.packaged.ex

data class ResultadoComparacion(
    val equipoConSecret : List<EquipoConSecretDTO>,
    val equipoSinPerfil : List<EquiposAsignados>
) : Serializable {
    override fun toString(): String {
        return " "
    }
}

data class EquipoConSecretDTO(
    val equipo : EquiposAsignados,
    val perfil : Map<String, String>
){}

data class PppSecreDTO(
    val id: String,
    val name:String = "",
    val service:String  ="",
    val password:String  ="",
    val profile :String = "",
    val remoteAddress:String = "",
    val callerId:String  ="",
    val comentario:String  =""
){}

fun parseMikrotik(profile:String):List<PppSecreDTO>{

     val secrets = mutableListOf<PppSecreDTO>()
    val lines = profile.split("\n")

    for (line in lines){
        val trimedLine = line.trim()
        if(trimedLine.isEmpty() || (!trimedLine.contains("name=") && !trimedLine.contains(".id="))){
            continue
        }
        val secret = PppSecreDTO(
            id = extractTerseValue(trimedLine, ".id"),
            name = extractTerseValue(trimedLine , "name"),
            service = extractTerseValue(trimedLine , "service"),
            password = extractTerseValue(trimedLine , "password"),
            profile = extractTerseValue(trimedLine , "profile"),
            remoteAddress = extractTerseValue(trimedLine , "remote-address"),
            callerId = extractTerseValue(trimedLine , "caller-id"),
            comentario = extractTerseValue(trimedLine , "comment")

        )
        secrets.add(secret)
    }

    return secrets
}


private fun extractTerseValue(line: String, key: String): String {
    val regex = Regex("""\b$key=([^\s=]+(?:\s(?!\w+=)[^\s=]+)*)""")
    return regex.find(line)?.groupValues?.get(1)?.trim() ?: ""
    /*val quotedPattern = Regex("$key=\"([^\"]*)")
    val unquotedPattern = Regex("$key=([^\\s]+)")

    val quotedMatch = quotedPattern.find(line)
    if (quotedMatch != null) {
        return quotedMatch.groupValues[1]
    }
    val unquotedMatch = unquotedPattern.find(line)
    if (unquotedMatch != null) {
        return unquotedMatch.groupValues[1]
    }

    return ""*/
}
