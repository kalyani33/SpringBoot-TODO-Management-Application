package com.example.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.todo.security.JwtAuthenticationEntryPoint;
import com.example.todo.security.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SpringSecurityConfig {
	private UserDetailsService userDetailsService;
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	private JwtAuthenticationFilter authenticationFilter;
	
	@Bean
	public static PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//http.csrf((csrf)-> csrf.disable())
		//http.csrf().disable()
		http.csrf((csrf)-> csrf.disable())
		.authorizeHttpRequests((authorize)->{
			authorize.requestMatchers(HttpMethod.POST,"/api/**").permitAll();
			//authorize.requestMatchers(HttpMethod.POST,"/api/**").hasRole("ADMIN");
//			authorize.requestMatchers(HttpMethod.PUT,"/api/**").hasRole("ADMIN");
//			authorize.requestMatchers(HttpMethod.DELETE,"/api/**").hasRole("ADMIN");
//			authorize.requestMatchers(HttpMethod.GET,"/api/**").hasAnyRole("ADMIN","USER");
//			authorize.requestMatchers(HttpMethod.PATCH,"/api/**").hasAnyRole("ADMIN","USER");
			//for publicly exposing the rest api
			//authorize.requestMatchers(HttpMethod.GET,"/api/**").permitAll();
			authorize.requestMatchers("/api/auth/**").permitAll();
			authorize.requestMatchers(HttpMethod.OPTIONS,"/**").permitAll();
			authorize.anyRequest().authenticated();
		}).httpBasic(Customizer.withDefaults());
		
		http.exceptionHandling(exception -> exception
				.authenticationEntryPoint(authenticationEntryPoint));
		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails user1 = User.builder()
//		.username("user1")
//		.password(getPasswordEncoder().encode("user1"))
//		.roles("USER")
//		.build();
//		
//		UserDetails admin = User.builder()
//				.username("admin")
//				.password(getPasswordEncoder().encode("admin"))
//				.roles("ADMIN")
//				.build();
//		return new InMemoryUserDetailsManager(user1,admin);
//	}

}
