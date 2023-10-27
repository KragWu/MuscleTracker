package fr.kragwu.muscletracker.userapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "fr.kragwu.muscletracker.userapi.repositories")
public class PersistenceConfig {
}
