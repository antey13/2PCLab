package company.config;

import company.config.properties.FlyDataSourceProperties;
import company.repository.fly.FlyBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
@DependsOn("transactionManager")
@EnableJpaRepositories(basePackageClasses = FlyBookingRepository.class, entityManagerFactoryRef = "flyEntityManager")
@EnableConfigurationProperties(FlyDataSourceProperties.class)
public class FlyBookingConfig {

    private final JpaVendorAdapter jpaVendorAdapter;
    private final FlyDataSourceProperties flyDataSourceProperties;

    @Bean(name = "flyDataSource", initMethod = "init", destroyMethod = "close")
    public DataSource flyDataSource() {
        var xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSourceClassName("org.postgresql.xa.PGXADataSource");
        xaDataSource.setUniqueResourceName(flyDataSourceProperties.getXaResourceName());

        var properties = new Properties();
        properties.setProperty("user", flyDataSourceProperties.getUser());
        properties.setProperty("password", flyDataSourceProperties.getPassword());
        properties.setProperty("serverName", flyDataSourceProperties.getServerName());
        properties.setProperty("portNumber", flyDataSourceProperties.getPort().toString());
        properties.setProperty("databaseName", flyDataSourceProperties.getDatabase());

        xaDataSource.setXaProperties(properties);

        return xaDataSource;
    }

    @Bean(name = "flyEntityManager")
    @DependsOn("transactionManager")
    public LocalContainerEntityManagerFactoryBean flyEntityManager(DataSource flyDataSource) {
        var properties = new HashMap<String, Object>();
        properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
        properties.put("javax.persistence.transactionType", "JTA");

        var entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setJtaDataSource(flyDataSource);
        entityManager.setJpaVendorAdapter(jpaVendorAdapter);
        entityManager.setPackagesToScan("company.model.fly");
        entityManager.setPersistenceUnitName("flyPersistenceUnit");
        entityManager.setJpaPropertyMap(properties);
        return entityManager;
    }

}
