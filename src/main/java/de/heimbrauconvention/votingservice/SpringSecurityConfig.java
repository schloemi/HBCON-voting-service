package de.heimbrauconvention.votingservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                	.antMatchers("/admin/**").hasAnyRole("ADMIN")
					.antMatchers("/**").permitAll()
					.anyRequest().authenticated()
                .and()
                .formLogin()
					.loginPage("/admin/login")
					.permitAll()
					.defaultSuccessUrl("/admin/dashboard")
				.and()
                .logout()
                	.logoutUrl("/admin/logout")
					.permitAll()
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID")
					.logoutSuccessUrl("/admin/login")
				.and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
       
        manager.createUser(User
          .withUsername("admin")
          .password(encoder().encode("password"))
          .roles("ADMIN")
          .build());
         
        return manager;
    }
     
    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver, SpringSecurityDialect sec) {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.addDialect(sec); // Enable use of "sec"
        return templateEngine;
    }

    
}
