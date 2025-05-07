package com.borntogeek.gmail_sender;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class GmailSender {
	private static final String EMAIL_FROM = "dunabplus@gmail.com";
	private static final String EMAIL_TO = "mvalera@unab.edu.co";
	private static final String APP_PASSWORD = "bncb eygp rujr prvs";
	
	public static void main(String[] args) throws Exception {
		Message message = new MimeMessage(getEmailSession());
		message.setFrom(new InternetAddress(EMAIL_FROM));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO));
		message.setSubject("🎉 ¡Registro exitoso! Bienvenido a nuestra plataforma");
message.setText(
    "Hola,👋\n\n" +
    "¡Bienvenido a Dunab+! \n\n" +
    "Tu cuenta ha sido creada con éxito y ya puedes empezar a gestionar tus finanzas con nosotros.\n" +
    "Desde ahora podrás llevar el control de tus ingresos, gastos y mucho más, todo desde una sola plataforma.\n\n" +
    "💰 Recuerda: en Dunab+, cada Đ cuenta. 😉\n\n" +
    "Si tienes preguntas o necesitas ayuda, estamos aquí para ti.\n\n" +
    "Gracias por confiar en nosotros,\n" +
    "El equipo de Dunab+"
);


		Transport.send(message);
	}
	
	private static Session getEmailSession() {
		return Session.getInstance(getGmailProperties(), new Authenticator() {
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(EMAIL_FROM, APP_PASSWORD);
		    }
		});
	}
	
	private static Properties getGmailProperties() {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		return prop;
	}
}
