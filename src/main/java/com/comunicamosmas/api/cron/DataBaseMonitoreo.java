package com.comunicamosmas.api.cron;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.Connection;

@Component
public class DataBaseMonitoreo {
	
	private final DataSource dataSource;
	
	@Autowired
	public DataBaseMonitoreo(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	@Autowired
    private JavaMailSender mailSender;
	
	private static final String DB_URL = "jdbc:mysql://192.168.10.21:33306/controlmas";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "mysql";

	//@Scheduled(cron="*/20 * * * * *") //(cron = "0 0 12 * * ?")
    public void checkDatabaseConnection() {
		try {
            Connection connection = (Connection) DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            connection.createStatement().execute("SELECT 1");
            connection.close();
            
            System.out.println("La conexión a la base de datos está activa.");
            
        } catch (SQLException e) {
        	
            System.err.println("La conexión a la base de datos ha fallado: " + e.getMessage());
            
            sendAlertEmail();
            // Aquí puedes enviar una alerta por correo electrónico o SMS
        }
    }

    private void sendAlertEmail() {
        try {
        	
        	MimeMessage message = mailSender.createMimeMessage();
        	
            MimeMessageHelper helper = new MimeMessageHelper(message);
            
            helper.setTo("web@internetinalambrico.com.co");
            
            helper.setSubject("Alerta: se perdió la conexión a la base de datos");
            
            helper.setText("La conexión a la base de datos se ha perdido. Por favor, investigue el problema lo antes posible.");
            
            mailSender.send(message);
            
        }catch(Exception e)
        {
        	System.out.print("error enviado correo: " + e.getMessage());
        }
    }
}
