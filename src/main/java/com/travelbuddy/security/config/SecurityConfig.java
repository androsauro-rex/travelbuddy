package com.travelbuddy.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.travelbuddy.security.filter.JwtAuthenticationFilter;


import java.util.List;
@Configuration
@EnableMethodSecurity // abilita la protezione sui metodi

public class SecurityConfig {


	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.csrf(csrf -> csrf.disable())
		.cors(Customizer.withDefaults())
		.authorizeHttpRequests(auth -> auth
				.requestMatchers("/*.html").permitAll()
				.requestMatchers("/style/**").permitAll()
				.requestMatchers("/script/**").permitAll()
				.requestMatchers("/assets/**").permitAll()
				.requestMatchers("/api/v1/public/**").permitAll()
				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // utile per CORS
				.requestMatchers("/api/v1/guest/**").hasRole("GUEST")
				.requestMatchers("/api/v1/user/**").hasRole("USER")
				.requestMatchers("/api/v1/moderator/**").hasRole("MODERATOR")
				.requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
				.requestMatchers("/api/v1/common/**").hasAnyRole("USER", "MODERATOR", "ADMIN")
				.requestMatchers("/api/v1/mod-content/**").hasAnyRole("MODERATOR", "ADMIN")
				.anyRequest().authenticated()
				)
		.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
		.addFilterBefore(jwtAuthenticationFilter,
				org.springframework.security.web.authentication
				.UsernamePasswordAuthenticationFilter.class
				);
		return http.build();
	}
	// Configurazione di CORS
	// Serve perché il frontend chiama AUTH su: http://localhost:8081
	//    @Bean
	//    CorsConfigurationSource corsConfigurationSource() {
	//        CorsConfiguration configuration = new CorsConfiguration();
	//        configuration.setAllowedOrigins(List.of(
	//                "http://localhost:8080", 
	//                "http://localhost:5500", 
	//                "http://127.0.0.1:5500"));
	//        //configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	//        //configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
	//        configuration.setAllowedMethods(List.of("*"));
	//        configuration.setAllowedHeaders(List.of("*"));
	//        configuration.setAllowCredentials(false);
	//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	//        source.registerCorsConfiguration("/**", configuration);
	//        return source;
	//    }
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("http://localhost")); // XAMPP
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	
	@Bean
	 PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}


}
