package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final UserDetailsService userDetails;

    public WebSecurityConfig(SuccessUserHandler successUserHandler, @Lazy UserDetailsService userDetails) {
        this.successUserHandler = successUserHandler;
        this.userDetails = userDetails;
    }

    protected void configure(AuthenticationManagerBuilder authenticationManager) throws Exception {
        authenticationManager.userDetailsService(userDetails);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                //.antMatchers("/api/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/admin/**", "api/**").hasRole("ADMIN")
                .antMatchers("/user/**", "api/user").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/")
                .successHandler(successUserHandler)
                .loginProcessingUrl("/")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    // аутентификация inMemory
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("user")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetails);
        return daoAuthenticationProvider;
    }
}