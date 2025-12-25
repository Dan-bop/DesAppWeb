package com.example.demo.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreo(String para, String asunto, String mensaje) {

        try {
            // Imagen por defecto (bienvenida)
            String imagenUrl = "https://res.cloudinary.com/db8ryaiba/image/upload/v1765693370/futbol_enrvsn.avif";

            // 🔍 Detectar imagen dinámica: [IMG=URL]
            if (mensaje.startsWith("[IMG=")) {
                int fin = mensaje.indexOf("]");
                imagenUrl = mensaje.substring(5, fin);
                mensaje = mensaje.substring(fin + 1).trim();
            }

            // ✅ Botón Pagar
            if (mensaje.contains("[BTN_PAGAR=")) {
                int ini = mensaje.indexOf("[BTN_PAGAR=") + 12;
                int fin = mensaje.indexOf("]", ini);
                String url = mensaje.substring(ini, fin);

                String boton =
                    "<div style='margin:16px 0; text-align:center;'>" +
                    "<a href='" + url + "' " +
                    "style='display:inline-block; padding:12px 20px; background:#16a34a; color:white; " +
                    "text-decoration:none; border-radius:8px; font-size:15px;'>Pagar reserva</a>" +
                    "</div>";

                mensaje = mensaje.replace("[BTN_PAGAR=" + url + "]", boton);
            }

            // ✅ Botón Ver Reserva
            if (mensaje.contains("[BTN_VER=")) {
                int ini = mensaje.indexOf("[BTN_VER=") + 9;
                int fin = mensaje.indexOf("]", ini);
                String url = mensaje.substring(ini, fin);

                String boton =
                    "<div style='margin:16px 0; text-align:center;'>" +
                    "<a href='" + url + "' " +
                    "style='display:inline-block; padding:12px 20px; background:#2563eb; color:white; " +
                    "text-decoration:none; border-radius:8px; font-size:15px;'>Ver mi reserva</a>" +
                    "</div>";

                mensaje = mensaje.replace("[BTN_VER=" + url + "]", boton);
            }

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(para);
            helper.setSubject(asunto);

            String contenidoHtml =
                "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "</head>" +
                "<body style=\"margin:0; padding:0; background-color:#f2f4f7;\">" +

                "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"padding:16px;\">" +
                "<tr><td align=\"center\">" +

                "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" " +
                "style=\"max-width:600px; background:#ffffff; border-radius:16px; overflow:hidden;" +
                "box-shadow:0 8px 25px rgba(0,0,0,0.1);\">" +

                "<tr>" +
                "<td>" +
                "<img src=\"" + imagenUrl + "\" style=\"width:100%; height:auto; display:block;\">" +
                "</td>" +
                "</tr>" +

                "<tr>" +
                "<td style=\"padding:28px; font-family:Arial, Helvetica, sans-serif;\">" +

                "<h2 style=\"margin:0 0 16px; color:#1f2933;\">Pacific Sport ⚽</h2>" +

                "<p style=\"font-size:16px; color:#374151; line-height:1.7;\">" +
                mensaje.replace("\n", "<br>") +
                "</p>" +

                "<div style=\"margin:24px 0; padding:16px; background:#f9fafb; border-left:4px solid #16a34a;\">" +
                "<p style=\"margin:0; font-size:14px;\">💡 Tip: revisa el calendario y asegura tu cancha con anticipación.</p>" +
                "</div>" +

                "<p style=\"margin-top:24px; font-size:13px; color:#6b7280; text-align:center;\">" +
                "© 2025 <strong>Pacific Sport</strong><br>" +
                "Juega más. Organiza mejor." +
                "</p>" +

                "</td>" +
                "</tr>" +

                "</table>" +
                "</td></tr>" +
                "</table>" +

                "</body>" +
                "</html>";

            helper.setText(contenidoHtml, true);
            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al enviar correo", e);
        }
    }
}
