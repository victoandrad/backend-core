package com.victoandrad.backend.infra.config.mail;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "app.mail")
public class MailProperties {

    private String from;
}
