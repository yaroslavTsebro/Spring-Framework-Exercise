package com.exercise.sfe.configuration;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@Profile("dev")
@PropertySource("classpath:datasource.properties")
@EnableTransactionManagement
@RequiredArgsConstructor
public class DatasourceConfiguration {

  private final Environment environment;

  @Bean
  public DataSource dataSource() {
    var dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(environment.getProperty("datasource.driver-class-name"));
    dataSource.setUrl(environment.getProperty("datasource.url"));
    dataSource.setUsername(environment.getProperty("datasource.username"));
    dataSource.setPassword(environment.getProperty("datasource.password"));

    return dataSource;
  }

  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean
  public TransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }
}
