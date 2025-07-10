package JForce.JForce.Service;
import JForce.JForce.Repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Service
public class EmailService {

    private JavaMailSender mailSender;
    private final StaffRepository staffRepository;


    /**
     * Constructor For Mail Sender
     * @param mailSender
     * @param staffRepository
     */
    public EmailService(JavaMailSender mailSender, StaffRepository staffRepository) {
        this.mailSender = mailSender;
        this.staffRepository = staffRepository;
    }
    /**
     * Sends a password reset email to the specified staff member.
     *
     * @param staffMail     The email address of the staff member.
     * @param username      The username of the staff member.
     * @param plainPassword The new (plain text) temporary password.
     */
    public void PasswordResetMail(String staffMail, String username, String plainPassword) {
        if (staffMail == null || staffMail.isBlank()) {
            System.err.println("Staff email is null or empty.");
            return;
        }

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(staffMail);
            helper.setSubject("Password Reset - JForce Inventory System");

            String emailContent = String.format("""
            <p>Dear %s,</p>
            <p>Your password has been reset.</p>
            <p><b>If you did not do this, please ignore the email.</b</p>
            <p><b>New Password:</b> %s</p>
            <p>Please log in using this password and change it immediately for security reasons.</p>
            <p>Please not that this token is only available 15 minutes after send another request 24 hours later!</p>
            <p>Best regards,<br>JForce Inventory Team</p>
        """, username, plainPassword);

            helper.setText(emailContent, true);
            mailSender.send(mimeMessage);

            System.out.println(" Password reset email sent to: " + staffMail);

        } catch (MessagingException e) {
            System.err.println(" Failed to send password reset email to: " + staffMail);
            e.printStackTrace();
        }
    }
}
