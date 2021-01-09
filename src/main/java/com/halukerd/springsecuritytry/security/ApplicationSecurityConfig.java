package com.halukerd.springsecuritytry.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.halukerd.springsecuritytry.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails albusDumbledoreUser = User.builder()
                .username("albus")
                .password(passwordEncoder.encode("sherbetlemon"))
                .authorities(HEADMASTER.getGrantedAuthorities())
                .build();

        UserDetails nevilleLongbottomUser = User.builder()
                .username("neville")
                .password(passwordEncoder.encode("1234"))
                .authorities(STUDENT.getGrantedAuthorities())
                .build();

        UserDetails percyUser = User.builder()
                .username("percy")
                .password(passwordEncoder.encode("iamprefect"))
                .authorities(PREFECT.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                albusDumbledoreUser,
                nevilleLongbottomUser,
                percyUser
        );
    }
}