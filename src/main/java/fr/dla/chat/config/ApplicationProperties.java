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
    private Activemq activemq;

    public static class Activemq {
        private String brokerUrl;

        public String getBrokerUrl() {
            return brokerUrl;
        }

        public void setBrokerUrl(String brokerUrl) {
            this.brokerUrl = brokerUrl;
        }
    }

    public Activemq getActivemq() {
        return activemq;
    }

    public void setActivemq(Activemq activemq) {
        this.activemq = activemq;
    }
}
