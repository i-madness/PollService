package net.imadness.services.management;

/*@Service
public class MailVerificationService {

    @Autowired
    private MailSender mailSender;

    */

import net.imadness.util.exceptions.MailVerificationException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
     * This method will send compose and send the message
     * *//*
    public void sendMail(String to, String subject, String body)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}*/

@Service
public class MailVerificationService {
    private Properties properties;
    private Session session;

    @PostConstruct
    public void init() {
        properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.imaps.ssl.trust", "*");
        session = Session.getDefaultInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("spoll.service@gmail.com", "w4nnaP011");
                    }
                });
    }

    public void send(String email, String title, String text) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("spoll.service@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(title);
            message.setText(text+"\n---------------------------\nBest regards, Valery");
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", 587, "spoll.service@gmail.com", "w4nnaP011");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            //Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new MailVerificationException();
        }
    }
}
