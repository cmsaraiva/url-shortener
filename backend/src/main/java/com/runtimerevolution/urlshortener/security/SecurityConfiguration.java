package com.runtimerevolution.urlshortener.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .inMemoryAuthentication()
        .withUser("frontend").password(passwordEncoder().encode("ol12!*Ac")).roles("USER")
        .and()
        .withUser("test").password(passwordEncoder().encode("test")).roles("USER");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http
        .antMatcher("/api/url/shorten")
        .authorizeRequests()
        .anyRequest().hasRole("USER")
        .and()
        .httpBasic()
        .and()
        .csrf().disable();

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
