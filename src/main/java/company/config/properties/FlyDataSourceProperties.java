package company.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "db.fly")
public class FlyDataSourceProperties {
    private Integer port;
    private String database;
    private String user;
    private String password;
    private String serverName;
    private String xaResourceName;
}
