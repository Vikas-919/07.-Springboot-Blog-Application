package com.codewithvikas.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.codewithvikas.blog.security.JwtAuthenticationEntryPoint;
import com.codewithvikas.blog.security.JwtAuthenticationFilter;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.websocket.Session;

@Configuration
@EnableMethodSecurity
@SecurityScheme(
		name="Bear Authentication",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
)
public class SecurityConfig {
	
	private UserDetailsService userDetailsService;
	
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	public SecurityConfig(UserDetailsService userDetailsService,
						  JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
						  JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.userDetailsService = userDetailsService;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// Configure AuthenticationManager in spring security configuration class...
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf((csrf) -> csrf.disable())
			.authorizeHttpRequests((authorize) -> 
					//authorize.anyRequest().authenticated()
					authorize.requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
							 .requestMatchers("/api/v1/auth/**").permitAll()
							 .requestMatchers("/swagger-ui/**").permitAll()
							 .requestMatchers("/v3/api-docs/**").permitAll()
							 .anyRequest().authenticated()
			// .permitAll() -> apart from HTTP GET request authentication is required...
			).exceptionHandling(exception -> exception
					.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			).sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			);
		
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	// !.....Not required this, as we are using DB Authentication.....!
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails vikas = User.builder()
//							.username("vikas")
//							.password(passwordEncoder().encode("vikas123"))
//							.roles("USER")
//							.build();
//		
//		UserDetails admin = User.builder()
//							.username("admin")
//						    .password(passwordEncoder().encode("admin123"))
//							.roles("ADMIN")
//							.build();
//		
//		return new InMemoryUserDetailsManager(vikas, admin);
//	}
}
