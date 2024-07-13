package com.personal_projects.notifications_qpi.integrations.emailservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class EmailServiceConfig {

    @Value("${email.service.baseurl}")
    public String baseUrl;
    @Value("${email.service.send}")
    public String sendEmailUrl;


}
