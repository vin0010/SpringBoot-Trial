package com.codex.trial.trial.model;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.codex.trial.trial")
@EntityScan("com.codex.trial.trial.model")
@SpringBootApplication
public class Application {

}