package com.holzhausen.MedHelper.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private BCryptPasswordEncoder passwordEncoder;

    private DataSource dataSource;

    @Value("$(spring.queries.users-query)")
    private String userQuery;

    @Value("$(spring.queries.role-query)")
    private String roleQuery;

    @Autowired
    public SecurityConfiguration(BCryptPasswordEncoder passwordEncoder, DataSource dataSource) {
        this.passwordEncoder = passwordEncoder;
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth){

        try{
            auth.jdbcAuthentication()
                    .usersByUsernameQuery(userQuery)
                    .authoritiesByUsernameQuery(roleQuery)
                    .dataSource(dataSource)
                    .passwordEncoder(passwordEncoder);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void configure(HttpSecurity http){

        try {
            http.authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/login").permitAll()
                    .anyRequest().authenticated().and().csrf()
                    .disable().formLogin()
                    .loginPage("/login").failureUrl("/login?error=true")
                    .defaultSuccessUrl("/")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .and().logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/");
        } catch (Exception e){
            e.printStackTrace();
        }


    }

}
