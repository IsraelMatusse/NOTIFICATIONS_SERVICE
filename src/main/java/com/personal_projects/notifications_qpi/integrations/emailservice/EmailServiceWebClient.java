package com.personal_projects.notifications_qpi.integrations.emailservice;


import com.personal_projects.notifications_qpi.dtos.request.EmailRequestDTO;
import com.personal_projects.notifications_qpi.dtos.response.EmailResponseDTO;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class EmailServiceWebClient {

    private final WebClient client;
    private final EmailServiceConfig emailServiceConfig;
    private final String baseUrl;
    private final String sendEmailUrl;
    private final Logger logger=org.slf4j.LoggerFactory.getLogger(EmailServiceWebClient.class);

    public EmailServiceWebClient(WebClient client, EmailServiceConfig emailServiceConfig) {
        this.client = client;
        this.emailServiceConfig=emailServiceConfig;
        this.baseUrl=emailServiceConfig.baseUrl;
        this.sendEmailUrl=emailServiceConfig.sendEmailUrl;

    }

    public void sendEmail(EmailRequestDTO dto)  {
        try {
            Mono<EmailResponseDTO> response = client.post()
                    .uri(baseUrl + sendEmailUrl)
                    .bodyValue(dto)
                    .retrieve()
                    .bodyToMono(EmailResponseDTO.class);
            EmailResponseDTO responseAPI = response.block();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }
}

