package com.github.danielprz1394.library.library_001_general_repositories.configurations;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class HikariConnection {

    @Value("${spring.datasource.url}")
    private String urlActive;
    @Value("${spring.datasource.driver-class-name}")
    private String driverActive;
    @Value("${spring.datasource.username}")
    private String usernameActive;
    @Value("${spring.datasource.password}")
    private String passwordActive;

    @Bean(name = "dataSourceActive")
    public DataSource generateDataSourceActive() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPoolName("Active database connection");
        hikariConfig.setDriverClassName(this.driverActive);
        hikariConfig.setJdbcUrl(this.urlActive);
        hikariConfig.setUsername(this.usernameActive);
        hikariConfig.setPassword(this.passwordActive);

        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate generateJdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
