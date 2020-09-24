package com.korshunov.spring.security;

import com.korshunov.spring.security.jwt.JwtSecurityConfigurer;
import com.korshunov.spring.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/operations/all").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/operations/operation/{id}").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/operations/filter/{filter}").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/article/{article}").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/operations/add").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/operations/operation/{id}").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/operations/operation/{id}").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/articles/all").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/articles/article/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/id_article/{name}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/articles/add").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/articles/article/{id}").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, "/articles/article/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/balances/all").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/balances/balance/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/balances/add").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/balances/balance/{id}").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, "/balances/balance/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/auth/signin").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/signup").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }
}
