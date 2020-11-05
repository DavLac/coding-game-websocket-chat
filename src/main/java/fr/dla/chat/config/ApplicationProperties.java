package fr.dla.chat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Chat.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
}
