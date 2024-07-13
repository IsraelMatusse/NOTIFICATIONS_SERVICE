package com.personal_projects.notifications_qpi.dtos.request;

import jakarta.validation.constraints.NotBlank;


public record EmailRequestDTO(

        @NotBlank(message = "emailFrom cannot be null")
        String emailTo,
        @NotBlank(message = "Subject cannot be null")
        String subject,
        @NotBlank(message = "Body cannot be null")
        String text)
{


}
