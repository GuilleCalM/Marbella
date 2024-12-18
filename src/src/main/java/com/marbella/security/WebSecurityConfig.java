package com.marbella.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
		return http
                .authorizeHttpRequests(authorize -> authorize
								.requestMatchers("/contactanos","/listarFavoritos", "/agregarFavorito", "agregarFavoritoCarrito").permitAll()
                                .requestMatchers("/index", "/", "/login", "/logout","/registrarCliente","/buscarProducto").permitAll()
                                .requestMatchers("/static/**", "/uploads/**", "/imagenes/**","/css/**").permitAll()
                                .requestMatchers("/categoria/**", "/carrito", "/agregar/**", "/eliminar/**", "/actualizarCarrito").permitAll()
                                .requestMatchers(HttpMethod.POST, "/agregar/**", "/actualizarCarrito","watson/**").permitAll()
                                .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .formLogin(login -> login 
                        .loginPage("/login")
						.successHandler(successHandler())
                        //.defaultSuccessUrl("/login?error=false")
                        .failureUrl("/login?error=true")
                        .permitAll())
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .sessionFixation()
                        .migrateSession()
                        .maximumSessions(1))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/index")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .permitAll())
                .build();
    }
	
	@Bean
	PasswordEncoder codificador() {
		return new BCryptPasswordEncoder();
	}
	
	public AuthenticationSuccessHandler successHandler (){
		return ((request, response, authentication) -> {
			response.sendRedirect("/login?error=false");
		});
	}

}