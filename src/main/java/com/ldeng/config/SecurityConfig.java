package com.ldeng.config;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ldeng.service.UserSecurityService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
    private Environment env;
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	private static final String SALT = "asdfwbdsfvb3145";
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
	}
	
	private static final String[] PUBLIC_MATCHERS = {
	        "/webjars/**",
	            "/css/**",
	            "/js/**",
	            "/images/**",
	            "/",
	            "/about/**",
	            "/contact/**",
	            "/error/**/*",
	            "/console/**"
	    };

	    @Override
	    protected void configure(HttpSecurity http) throws Exception {

//	        List<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
//	        if (activeProfiles.contains("dev")) {
//	            http.csrf().disable();
//	            http.headers().frameOptions().disable();
//	        }

	        http
	                .authorizeRequests()
	                .antMatchers(PUBLIC_MATCHERS).permitAll()
	                .anyRequest().authenticated()
	                .and()
	                .formLogin().loginPage("/login").defaultSuccessUrl("/payload")
	                .failureUrl("/login?error").permitAll()
	                .and()
	                .logout().permitAll();
	    }
	    
	    @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//	    	 auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
	    	auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	    }

}
