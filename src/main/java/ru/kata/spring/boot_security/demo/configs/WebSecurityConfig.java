package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Configuration // указываем класс, который используется для описания конфигурации Спринг
@EnableWebSecurity  // для включения конфигурации безопастности для вэб-приложений
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {  // наследуемся для определения правил безопастности
    private final UserServiceImpl userServiceImpl;
    private final SuccessUserHandler successUserHandler;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(UserServiceImpl userServiceImpl, SuccessUserHandler successUserHandler, PasswordEncoder passwordEncoder) {
        this.successUserHandler = successUserHandler;
        this.userServiceImpl = userServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userServiceImpl)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/", "/welcome")
                .permitAll()
                .antMatchers("/admin/**")
                .hasRole("ADMIN")
                .antMatchers("/user/**")
                .hasAnyRole("USER", "ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .successHandler(successUserHandler)
                .loginPage("/login")
                .loginProcessingUrl("/process_login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
}




