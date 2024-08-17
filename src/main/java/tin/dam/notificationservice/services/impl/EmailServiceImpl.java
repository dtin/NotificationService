package tin.dam.notificationservice.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import tin.dam.notificationservice.configs.AppConfig;
import tin.dam.notificationservice.dtos.MessageDTO;
import tin.dam.notificationservice.services.EmailService;

import java.nio.charset.StandardCharsets;

@Service
@Log4j2
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private AppConfig appConfig;

    @Override
    @Async
    public void sendEmail(MessageDTO messageDTO) {
        try {
            log.info("Start sendEmail");

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());

            // Load template email with content
            Context context = new Context();
            context.setVariable("name", messageDTO.getToName());
            context.setVariable("content", messageDTO.getContent());
            String html = templateEngine.process("welcome-email", context);

            // Send email
            helper.setTo(messageDTO.getToEmail());
            helper.setText(html, true);
            helper.setSubject(messageDTO.getSubject());
            helper.setFrom(appConfig.getFrom());
            javaMailSender.send(message);

            log.info("Send email success...");
        } catch(MessagingException ex) {
            log.error("Email send with error: {}", ex);
        }

        log.info("End sendEmail");
    }
}
