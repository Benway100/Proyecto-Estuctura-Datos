/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_inter;

/**
 *
 * @author mvale
 */
import com.sun.mail.imap.protocol.ID;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EnviarCorreo {

    public static void enviar(String correo, String nombre, String id, String contra,String apodo) {
        final String remitente = "dunabplus@gmail.com"; // tu correo
        final String clave = "bncb eygp rujr prvs"; // tu contrase?a o contrase?a de aplicaci?n
        final String destinatario =correo; // a qui�n le enviar�s

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("🎉 ¡Registro exitoso! Bienvenido a nuestra plataforma");
            

            message.setText("Hola,👋\n\n" +
    "¡Bienvenido a Dunab+! \n\n" +
    "Tu cuenta ha sido creada con éxito y ya puedes empezar a gestionar tus finanzas con nosotros.\n" +
    "Desde ahora podrás llevar el control de tus ingresos, gastos y mucho más, todo desde una sola plataforma.\n\n" +
    "Tu cuenta en Dunab+ esta inscrita con estos datos:\n"+
                    
    "Nombre: "+nombre+
    "\nApodo: "+apodo+
    "\nID: "+id+
    "\nCorreo: "+correo+
    "\nContraseña: "+contra+
    
    "\n💰 Recuerda: en Dunab+, cada Đ cuenta. 😉\n\n" +
    "Si tienes preguntas o necesitas ayuda, estamos aquí para ti.\n\n" +
    "Gracias por confiar en nosotros,\n" +
    "El equipo de Dunab+");

            Transport.send(message);

            System.out.println("Correo enviado exitosamente.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
