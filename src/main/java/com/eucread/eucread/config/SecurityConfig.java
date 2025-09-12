package com.eucread.eucread.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.eucread.eucread.users.auth.filter.JwtFilter;
import com.eucread.eucread.users.auth.filter.UserStatusValidationFilter;
import com.eucread.eucread.users.auth.handler.CustomLoginFailureHandler;
import com.eucread.eucread.users.auth.handler.CustomLoginSuccessHandler;
import com.eucread.eucread.users.auth.service.CustomUserDetailsService;
import com.eucread.eucread.util.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private static final List<String> CORS_WHITELIST = List.of(
		"http://localhost:5173"
	);
	private static final List<String> WHITELIST = List.of(
		"/api/user/login",
		"/api/user/register",
		"/api/user/check-email",
		"/api/user/check-username",
		"/api/user/auth/email"
	);

	private final CustomUserDetailsService customUserDetailsService;
	private final CustomLoginSuccessHandler customLoginSuccessHandler;
	private final CustomLoginFailureHandler customLoginFailureHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter, UserStatusValidationFilter userStatusValidationFilter) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable);

		http
			.formLogin(form -> form
				.loginPage("/api/user/login")
				.usernameParameter("email")
					.passwordParameter("password")
					.successHandler(customLoginSuccessHandler)
					.failureHandler(customLoginFailureHandler)
				)
				.userDetailsService(customUserDetailsService)
			.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
			);

		http
			.addFilterBefore(userStatusValidationFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(WHITELIST.toArray(new String[0])).permitAll()
				.requestMatchers("/api/auth/creator/**").hasAnyRole("CREATOR", "ADMIN")
				.requestMatchers("/api/auth/reader/**").hasAnyRole("READER", "ADMIN")
				.requestMatchers("/api/auth/translator/**").hasAnyRole("TRANSLATOR", "ADMIN")
				.requestMatchers("/api/auth/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()
			);

		http
			.cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()));

		return http.build();
	}

	private CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(CORS_WHITELIST);
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Collections.singletonList("*"));
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}