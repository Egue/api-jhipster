package com.hj_solutions.isp_api.service
import com.jcraft.jsch.*
class ShhClient(){

    fun execute(
        username:String,
        host:String,
        port:Int,
        password:String,
        comando:String
    ):String{
        val jsch = JSch()
        val session = jsch.getSession(username , host, port)
        session.setPassword(password)
        //
        session.setConfig("StrictHostKeyChecking" , "no")
        session.connect()

        val channel = session.openChannel("exec") as ChannelExec
        channel.setCommand(comando)
        channel.inputStream = null
        val input = channel.inputStream

        channel.connect()

        val output = input.bufferedReader().use {it.readText()}

        channel.disconnect()
        session.disconnect()

        return output
    }
}