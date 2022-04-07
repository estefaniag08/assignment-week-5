package dev.applaudostudios.examples.assignmentweek5.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("dev.applaudostudios.examples.assignmentweek5")
public class WebMvcConfiguration implements WebMvcConfigurer {

}
