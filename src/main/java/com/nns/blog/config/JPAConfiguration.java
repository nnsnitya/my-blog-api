package com.nns.blog.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class JPAConfiguration {

    @Value("${spring.datasource.url}")
    private String URL;
    @Value("${spring.datasource.username}")
    private String USER_NAME;
    @Value("${spring.datasource.password}")
    private String PASSWORD;
    @Value("${pool.name}")
    private String poolName;
    @Value("${spring.datasource.hikari.connection-timeout}")
    private int connectionTimeout;
    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int poolSize;
    @Value("${spring.datasource.hikari.idle-timeout}")
    private int idleTimeout;
    @Value("${spring.datasource.hikari.max-lifetime}")
    private int maxLife;
    @Value("${spring.datasource.hikari.min-idle}")
    private int minIdle;

    @Bean
    public DataSource getDataSource() {
        System.out.println("Datasource bean creating..");
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL.strip());
        config.setUsername(USER_NAME.strip());
        config.setMaximumPoolSize(poolSize);
        config.setMaxLifetime(maxLife);
        config.setConnectionTimeout(connectionTimeout);
        config.setIdleTimeout(idleTimeout);
        config.setPoolName(poolName);
        config.setMinimumIdle(minIdle);
        config.setPassword(PASSWORD);
        return new HikariDataSource(config);
    }
}
