package tin.dam.notificationservice.services.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tin.dam.notificationservice.dtos.MessageDTO;
import tin.dam.notificationservice.services.EmailService;

@Service
@Log4j2
public class MessageServiceImpl {
    @Autowired
    private EmailService emailService;

    @KafkaListener(id = "notificationGroup", topics = "notification")
    public void listen(MessageDTO messageDTO) {
        log.info("Received: {}", messageDTO.getToEmail());
        emailService.sendEmail(messageDTO);
    }
}
