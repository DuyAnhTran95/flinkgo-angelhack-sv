package vn.crazyx.flinkgo.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import vn.crazyx.flinkgo.service.token.TokenService;
import vn.crazyx.flinkgo.service.user.UserService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    UserService userService;
    
    @Autowired
    TokenService tokenService;
    
    @Bean
    public AuthenticationProvider authProvider() {
        return new FlinkgoUserDetailAuthenticationProvider(tokenService);
    }
    
    @Bean
    public AuthenticationEntryPoint entry() {
        return new FlinkgoAuthenticationEntryPoint();
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
    }
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()
            .cors().disable()
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(entry())
            .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
                .antMatchers("/", "/test").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
            .and()         
            .logout()
                .logoutUrl("/logout").invalidateHttpSession(false)
                .logoutSuccessUrl("/")
            .and()
            .addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class)
            .addFilterBefore(new FlinkgoRefreshAuthenticationFilter("/auth", userService, tokenService), 
                    UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new FlinkgoLoginAuthenticationFilter("/login", userService, tokenService), 
                    UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new FlinkgoTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            ;
        super.configure(http);
    }
}
