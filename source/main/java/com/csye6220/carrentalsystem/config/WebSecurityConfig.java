
package com.csye6220.carrentalsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;

import com.csye6220.carrentalsystem.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private final UserService userService;

	public WebSecurityConfig(UserService userService) {
		this.userService = userService;
	}

	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/").permitAll()
//              			.requestMatchers("/", "/user", "*/all", "*/byCarType", "*/byLocation", "/error", "/login", "/logout", "/reservations/**", "/users", "/cars/**", "/rental-agencies/**").permitAll()
//                        .requestMatchers("*/add", "/cars/edit/**", "/cars/all").hasAnyAuthority("ADMIN", "AGENCYSTAFF")
				.anyRequest().authenticated()).httpBasic(Customizer.withDefaults())
				.formLogin((form) -> form.loginPage("/login").permitAll().loginProcessingUrl("/login")
						.defaultSuccessUrl("/user", true).failureHandler((request, response, exception) -> {
							System.out.println(exception.getMessage());
							String errorMessage;
							if (exception instanceof UsernameNotFoundException) {
								errorMessage = "User not found";
							} else if (exception instanceof BadCredentialsException) {
								errorMessage = "Invalid password";
							} else {
								errorMessage = "Authentication failed";
							}

							request.getSession().setAttribute("error", errorMessage);
							new DefaultRedirectStrategy().sendRedirect(request, response, "/login?error=true");
						}))
				.logout((logout) -> logout.permitAll().logoutUrl("/"));

		return http.build();
	}
}