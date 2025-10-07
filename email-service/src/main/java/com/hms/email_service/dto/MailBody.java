package com.hms.email_service.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailBody {

    private String to;

    private String subject;

    private String text;

}
