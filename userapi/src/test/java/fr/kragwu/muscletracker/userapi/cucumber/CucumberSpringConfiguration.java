package fr.kragwu.muscletracker.userapi.cucumber;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.kragwu.muscletracker.userapi.UtilisateurApplication;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@CucumberContextConfiguration
@SpringBootTest(classes = {UtilisateurApplication.class, CucumberSpringConfiguration.PostgresTestConfiguration.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CucumberSpringConfiguration {

    static PostgreSQLContainer postgreSQLContainer;

    @BeforeAll
    public static void startBDD() {
        String dockerImageName = "postgres:15";
        postgreSQLContainer = new PostgreSQLContainer<>(dockerImageName)
                .withDatabaseName("client")
                .withInitScript("database/initbdd.sql")
                .withExposedPorts(5432)
                .withUsername("sa")
                .withPassword("sa");
        System.out.println("-> Start PostgreSQL");
        postgreSQLContainer.start();
        System.out.println(postgreSQLContainer.getJdbcUrl());
    }

    @TestConfiguration
    static class PostgresTestConfiguration {
        @Bean
        DataSource dataSource() {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(postgreSQLContainer.getJdbcUrl());
            hikariConfig.setUsername(postgreSQLContainer.getUsername());
            hikariConfig.setPassword(postgreSQLContainer.getPassword());
            return new HikariDataSource(hikariConfig);
        }
    }

    @AfterAll
    public static void stopBDD() {
        postgreSQLContainer.stop();
        System.out.println("-> Stop PostgreSQL");
    }
}
