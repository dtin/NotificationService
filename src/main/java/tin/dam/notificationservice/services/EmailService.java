package tin.dam.notificationservice.services;

import tin.dam.notificationservice.dtos.MessageDTO;

public interface EmailService {
    void sendEmail(MessageDTO messageDTO);
}
