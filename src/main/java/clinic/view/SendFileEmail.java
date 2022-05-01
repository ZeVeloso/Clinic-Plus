package clinic.view;

import clinic.view.Box.AlertBox;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendFileEmail {

    public static void main() {

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
        final String username = "clinicaappmais@gmail.com";//
        final String password = "ClinicaPaulo";
        try{
            Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            // -- Create a new message --
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("clinicaappmais@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse("veloso.tribos@gmail.com"));
            message.setSubject("Backup DB");

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formatDateTime = now.format(formatter);

            String msg = "Database Backup " + formatDateTime;
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(new File("ClinicDB.db"));

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            multipart.addBodyPart(attachmentBodyPart);
            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Backup sent.");
            AlertBox.display("Backup", "Backup enviado com sucesso");
        }catch (MessagingException | IOException e){
            System.out.println("Erro, provavelmente nao tens net" + e);
            AlertBox.display("Backup", "Backup com erro, reportar o erro");
        }
    }
}