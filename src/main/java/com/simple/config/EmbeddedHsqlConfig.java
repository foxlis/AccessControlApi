package com.simple.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.dialect.HSQLDialect;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * Embedded Hsql DB configuration.
 * <p/>
 * @since Jun 9, 2015
 * @author ddziadek <a href="http://www.softwaremind.pl">SoftwareMind</a>
 */
@Configuration
@Import(JpaConfig.class)
public class EmbeddedHsqlConfig {

  @Autowired
  private JpaProperties jpaProperties;

  @Autowired
  private Properties hibernateCommonProperties;

  /**
   * Data source.
   * NOTE: DB schema must be created by specified SQL script before data population.
   *       Use DataSourceInitializer instead.
   *
   * @return the data source
   */
  @Bean
  public DataSource dataSource() {

    final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder() //
      .setName("rest-demo") //
      .setType(EmbeddedDatabaseType.HSQL);

    return builder.build();
  }

  /**
   * Entity manager factory.
   *
   * @return the entity manager factory
   */
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

    final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setDatabase(Database.HSQL);
    vendorAdapter.setDatabasePlatform(HSQLDialect.class.getName());
    vendorAdapter.setGenerateDdl(true);
    vendorAdapter.setShowSql(jpaProperties.isShowSql());

    final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setDataSource(dataSource());
    factory.setJpaProperties(hibernateHsqlProperties());
    factory.setJpaVendorAdapter(vendorAdapter);
    factory.setPackagesToScan(JpaConfig.ENTITY_PACKAGE);
    factory.setPersistenceProvider(new HibernatePersistenceProvider());
    factory.afterPropertiesSet();

    return factory;
  }

  /**
   * Hibernate specified HSQLDB properties.
   *
   * @return the properties
   */
  private Properties hibernateHsqlProperties() {

    final Properties hibernateProperties = new Properties(hibernateCommonProperties);
    hibernateProperties.setProperty("hibernate.connection.autocommit", Boolean.TRUE.toString());
    hibernateProperties.setProperty("hibernate.dialect", HSQLDialect.class.getName());
    hibernateProperties.setProperty("hibernate.hbm2ddl.auto", //
                                    jpaProperties.getHibernateProperties(dataSource()) //
                                    .get("hibernate.hbm2ddl.auto"));
    return hibernateProperties;
  }
}
