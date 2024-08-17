package tin.dam.notificationservice.dtos;

import lombok.Data;

@Data
public class MessageDTO {
    private String toEmail;
    private String toName;
    private String subject;
    private String content;
}
