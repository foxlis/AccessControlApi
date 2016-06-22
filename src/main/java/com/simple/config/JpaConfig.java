package com.simple.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main JPA configurator.
 * <p/>
 * @since Jun 8, 2015
 * @author ddziadek <a href="http://www.softwaremind.pl">SoftwareMind</a>
 */
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = { "com.simple.model" })
@EnableTransactionManagement
public class JpaConfig {

  /** The Constant ENTITY_PACKAGE. */
  public static final String ENTITY_PACKAGE = "com.simple.model";

  @Autowired
  private JpaProperties jpaProperties;

  /**
   * Exception translation.
   *
   * @return the persistence exception translation post processor
   */
  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

  /**
   * Hibernate common properties.
   *
   * @return the properties
   */
  @Bean
  public Properties hibernateCommonProperties() {

    final Properties hibernateProperties = new Properties();
    hibernateProperties.setProperty("hibernate.default_entity_mode", "pojo");
    hibernateProperties.setProperty("hibernate.format_sql", Boolean.TRUE.toString());
    hibernateProperties.setProperty("hibernate.generate_statistics", Boolean.FALSE.toString());
    hibernateProperties.setProperty("hibernate.max_fetch_depth", "4");
    hibernateProperties.setProperty("hibernate.show_sql", String.valueOf(jpaProperties.isShowSql()));
    hibernateProperties.setProperty("hibernate.use_sql_comments", Boolean.FALSE.toString());

    hibernateProperties.setProperty("hibernate.cache.use_query_cache", Boolean.FALSE.toString());
    hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", Boolean.FALSE.toString());
    hibernateProperties.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");

    hibernateProperties.setProperty("hibernate.connection.charSet", "utf8");
    hibernateProperties.setProperty("hibernate.connection.characterEncoding", "utf8");
    hibernateProperties.setProperty("hibernate.connection.fixedString", Boolean.TRUE.toString());
    hibernateProperties.setProperty("hibernate.connection.release_mode", "auto");

    hibernateProperties.setProperty("hibernate.current_session_context_class", //
                                    "org.springframework.orm.hibernate4.SpringSessionContext");

    hibernateProperties.setProperty("hibernate.id.new_generator_mappings", Boolean.TRUE.toString());

    // enable JDBC batching to prevent from OOME if the second-level cache is/will be enabled
    hibernateProperties.setProperty("hibernate.jdbc.batch_size", "25");
    hibernateProperties.setProperty("hibernate.jdbc.use_streams_for_binary", Boolean.TRUE.toString());
    hibernateProperties.setProperty("hibernate.query.substitutions", "true 1, false 0, yes 'Y', no 'N'");

    hibernateProperties.setProperty("hibernate.transaction.auto_close_session", Boolean.TRUE.toString());
    hibernateProperties.setProperty("hibernate.transaction.factory_class", //
                                    "org.hibernate.engine.transaction.internal.jdbc.JdbcTransactionFactory");
    hibernateProperties.setProperty("hibernate.transaction.flush_before_completion", Boolean.TRUE.toString());

    return hibernateProperties;
  }

  /**
   * JPA Transaction manager.
   *
   * @param emf the Entity Manager Factory
   * @return the platform transaction manager
   */
  @Bean
  public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
    final JpaTransactionManager txManager = new JpaTransactionManager(emf);
    txManager.setDefaultTimeout(10);
    txManager.afterPropertiesSet();

    return txManager;
  }
}
