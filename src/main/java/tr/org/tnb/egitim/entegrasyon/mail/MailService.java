package tr.org.tnb.egitim.entegrasyon.mail;

import javax.mail.Message.RecipientType;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("mailService")
@Profile("production")
public class MailService {

    @Autowired
    private JavaMailSender mailSender;
    
    @Async
    public void sendSimpleEmail(String[] to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Async
    public void sendHtmlEmail(String[] to, String subject, String text) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            String recipients = "";
        	for (int i = 0; i < to.length; i++) {
        		recipients += to[i];
        		if (i < to.length - 1) {
        			recipients += ",";
        		}
        	}
            mimeMessage.setRecipients(RecipientType.TO, recipients);
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(text, "text/html; charset=utf-8");
            mailSender.send(mimeMessage);
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
    }

}