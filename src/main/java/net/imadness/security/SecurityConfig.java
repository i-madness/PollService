package net.imadness.security;

import net.imadness.services.management.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SettingsService settingsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(settingsService.getSettings().getLogin()).password(settingsService.getSettings().getPassword()).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/manage/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/manage/settings/**").access("hasRole('ROLE_ADMIN')")
                .and().formLogin().loginPage("/auth")
                .usernameParameter("ssoId").passwordParameter("password")
                .and().csrf().disable();

    }
}
