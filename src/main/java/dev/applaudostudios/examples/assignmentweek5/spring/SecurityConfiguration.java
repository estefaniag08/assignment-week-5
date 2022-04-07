package dev.applaudostudios.examples.assignmentweek5.spring;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.authorizeRequests()
                .antMatchers(HttpMethod.GET).permitAll()
                .anyRequest().authenticated()
                .and()
                    .csrf().disable()
                    .httpBasic()
                .and()
                    .build();


    }
}
