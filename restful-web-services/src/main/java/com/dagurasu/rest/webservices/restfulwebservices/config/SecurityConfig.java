package com.dagurasu.rest.webservices.restfulwebservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf()
			.disable()
			.authorizeRequests()
			.antMatchers("/h2-console/**")
			.permitAll()
			.mvcMatchers(HttpMethod.GET, "/users")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
		
		http.headers()
			.frameOptions()
			.disable();
		
		return http.build();
	}
	
}
