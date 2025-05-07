/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_inter;

/**
 *
 * @author mvale
 */
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EnviarCorreo {

    public static void main(String[] args) {
        final String remitente = "dunabplus@gmail.com"; // tu correo
        final String clave = "bncb eygp rujr prvs"; // tu contrase?a o contrase?a de aplicaci?n
        final String destinatario = "mvalera@unab.edu.co"; // a qui�n le enviar�s

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
            String html = """
    <div style='font-family: Arial, sans-serif; font-size: 14px; color: #333;'>
        <p style='font-size: 16px;'>Hola 👋</p>

        <p><strong>¡Bienvenido a <span style='color:#4CAF50;'>Dunab+</span>! 🎉</strong></p>

        <p>
            Tu cuenta ha sido creada con éxito y ya puedes empezar a gestionar tus finanzas con nosotros.<br>
            Desde ahora podrás llevar el control de tus <strong>ingresos</strong>, <strong>gastos</strong> y mucho más, todo desde una sola plataforma.
        </p>

        <p style='color: #00897B; font-weight: bold;'>💰 Recuerda: en Dunab+, cada Đ cuenta. 😉</p>

        <p>
            Si tienes preguntas o necesitas ayuda, <em>estamos aquí para ti</em>.
        </p>

        <p>Gracias por confiar en nosotros,</p>

        <p><strong>El equipo de Dunab+</strong></p>
    </div>
    """;

            message.setContent(html, "text/html");

            Transport.send(message);

            System.out.println("Correo enviado exitosamente.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
