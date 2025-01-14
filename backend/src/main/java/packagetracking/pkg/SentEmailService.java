package packagetracking.pkg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SentEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmailId;

    public void sendPackageAssignedEmail(String recipient, String body, String subject) {
        // Create an instance of SimpleMailMessage
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        // Set the email details
        simpleMailMessage.setFrom(fromEmailId);    // Correct use of the instance method
        simpleMailMessage.setTo(recipient);         // Correct use of the instance method
        simpleMailMessage.setSubject(subject);     // Set the subject
        simpleMailMessage.setText(body);           // Set the body text

        // Send the email
        javaMailSender.send(simpleMailMessage);
    }
}
