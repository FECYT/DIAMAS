package es.soltel.recolecta.converters;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mail")
public class MailProperties {

    private String host;
    private String port;
    private String passwordSMTP;
    private String userSMTP;
    private String from;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPasswordSMTP() {
        return passwordSMTP;
    }

    public void setPasswordSMTP(String passwordSMTP) {
        this.passwordSMTP = passwordSMTP;
    }

    public String getUserSMTP() {
        return userSMTP;
    }

    public void setUserSMTP(String userSMTP) {
        this.userSMTP = userSMTP;
    }
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }


}
