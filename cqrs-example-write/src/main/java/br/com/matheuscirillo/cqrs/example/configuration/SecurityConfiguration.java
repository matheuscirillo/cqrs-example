package br.com.matheuscirillo.cqrs.example.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import br.com.matheuscirillo.cqrs.example.infrastructure.repository.TokenRepository;
import br.com.matheuscirillo.cqrs.example.security.SecurityFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private TokenRepository tokenRepository;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http.authorizeRequests()
		.anyRequest().authenticated()
	.and()
		.addFilterAt(new SecurityFilter(tokenRepository), BasicAuthenticationFilter.class)
	.csrf().disable();
    }
    
}
