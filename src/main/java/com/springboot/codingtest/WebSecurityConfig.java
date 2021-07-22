package com.springboot.codingtest;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/").permitAll()
				.antMatchers("/mobiquity-services/bank-master/*").hasAnyRole("USER").anyRequest().authenticated().and()
				.httpBasic();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("user")
				.password("$2a$10$eY148XxqFyGMMRF3YfZZcuchSQxMNHPm1SBzntYNzjihrkmYLUO6.").roles("USER");
	}

}
