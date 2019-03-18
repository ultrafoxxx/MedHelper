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

    private String userQuery;

    private String roleQuery;

    @Autowired
    public SecurityConfiguration(BCryptPasswordEncoder passwordEncoder, DataSource dataSource) {
        this.passwordEncoder = passwordEncoder;
        this.dataSource = dataSource;
        userQuery = "select email, password, czy_konto_potwierdzone from user where email=?";
        roleQuery = "select email, rola from user where email=?";
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
                    .antMatchers("/logowanie").anonymous()
                    .anyRequest().authenticated().and().csrf()
                    .disable().formLogin()
                    .loginPage("/logowanie").failureUrl("/logowanie?error=true")
                    .defaultSuccessUrl("/")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .and().logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .and().rememberMe().key("secretKey").rememberMeParameter("remember-me");
        } catch (Exception e){
            e.printStackTrace();
        }


    }

}
