package dev.applaudostudios.examples.assignmentweek5.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("dev.applaudostudios.examples.assignmentweek5")
@EnableJpaRepositories("dev.applaudostudios.examples.assignmentweek5")
@EntityScan("dev.applaudostudios.examples.assignmentweek5.persistence.model")
public class AssignmentWeek5Application {

	public static void main(String[] args) {
		SpringApplication.run(new Class[] {AssignmentWeek5Application.class, WebMvcConfiguration.class, SecurityConfiguration.class}, args);
	}

}
