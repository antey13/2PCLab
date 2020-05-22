package company.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "db.hotel")
public class HotelDataSourceProperties {
    private Integer port;
    private String database;
    private String user;
    private String password;
    private String serverName;
    private String xaResourceName;
}
