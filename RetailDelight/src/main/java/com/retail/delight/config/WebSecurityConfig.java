package com.retail.delight.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.retail.delight.service.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Lazy
   @Autowired
   UserDetailsServiceImpl userDetailsService;
	//to  encrypt the password using hashing technique and send the encoded password
   @Bean
   public BCryptPasswordEncoder passwordEncoder() {
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
      return bCryptPasswordEncoder;
   }
   //Allows for easily building in memory authentication, JDBC based authentication, adding UserDetailsService, and adding AuthenticationProvider's.
   @Override
   public void configure(AuthenticationManagerBuilder auth) throws Exception {

      // Setting Service to find User in the database.
      // And Setting PassswordEncoder
      auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

   }

   //login configuration
   @Override
   protected void configure(HttpSecurity http) throws Exception {

      http.csrf().disable();

      // Requires login with role ROLE_EMPLOYEE or ROLE_MANAGER.
      // If not, it will redirect to /admin/login.
      http.authorizeRequests().antMatchers("/admin/accountInfo")//
            .access("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_MANAGER')");

      // Pages only for MANAGER
      http.authorizeRequests().antMatchers("/admin/product").access("hasRole('ROLE_MANAGER')");
      http.authorizeRequests().antMatchers("/admin/forgotPassword").access("hasRole('ROLE_MANAGER')");

      // When user login, role XX.
      // But access to the page requires the YY role,
      // An AccessDeniedException will be thrown.
      http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

      // Configuration for Login Form.
      http.authorizeRequests().and().formLogin()//

            //
            .loginProcessingUrl("/j_spring_security_check") // Submit URL
            .loginPage("/admin/login")//
            .defaultSuccessUrl("/admin/accountInfo")//
            .failureUrl("/admin/login?error=true")//
            .usernameParameter("userName")//
            .passwordParameter("password")

            // Configuration for the Logout page.
            // (After logout, go to home page)
            .and().logout().logoutUrl("/admin/logout").logoutSuccessUrl("/");

   }
}