package com.bank.application.bankingapplication.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        try {
            http
                    .authorizeHttpRequests((authorize) -> {
                                try {
                                    authorize
                                            .anyRequest().authenticated()
                                            .and()
                                            .httpBasic()
                                            .and()
                                            .csrf()
                                            .disable()
                                            /*
                                                 - By default user authentication are stored in http session.

                                                 - HttpSession provides a way to identify a user across more than one
                                                   page request or visit to a Web site and to store information about
                                                   that user.

                                                 - Since RestAPIs are independent and stateless, so while using postman
                                                   httpsession will be unique and user need to do authentication on
                                                   every endpoint. Hence, use .sessionManagement()
                                                   .sessionCreationPolicy(SessionCreationPolicy.STATELESS) which
                                                   tells spring to not create HTTP Session for storing user authentication.
                                                   in place return token in response which can be used to be authenticated.
                                                   when apis are hit from postman.
                                                   When using web applications user authentication are stored in http session.
                                            */
                                            .sessionManagement()
                                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    );
        } catch (Exception ex) {
            throw new RuntimeException("User not authenticated!");
        }
        return http.build();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
