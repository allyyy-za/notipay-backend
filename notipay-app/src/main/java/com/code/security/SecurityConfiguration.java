package com.code.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.code.services.AccountDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private AccountDetailsService accountDetailsService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	public SecurityConfiguration(AccountDetailsService accountDetailsService) {
		this.accountDetailsService = accountDetailsService;
	}

	 @Override @Bean
	 public AuthenticationManager authenticationManagerBean() throws Exception {
		 return super.authenticationManagerBean();
	 }
	 
	  @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(accountDetailsService)
	            .passwordEncoder(passwordEncoder());
	    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors()
			.and()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(
				(request, response, ex) -> {
					response.sendError(
	                HttpServletResponse.SC_UNAUTHORIZED,
	                ex.getMessage());
	             }
			);
		http
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
			.antMatchers(HttpMethod.GET, "/api/auth/**").permitAll()
			.antMatchers("/addBill").permitAll()
			.anyRequest().authenticated();
			
		
//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//			.and()
//			.addFilter(new JwtAuthenticationFilter(authenticationManager()))
//			.addFilter(new JwtAuthorizationFilter(authenticationManager(), this.userDao))
//			.authorizeRequests()
//			.antMatchers(HttpMethod.POST, "/api/signup").permitAll()
//			.antMatchers(HttpMethod.POST, "/api/signin").permitAll();
//			.and()
//			.formLogin()
//			.loginPage("/signin").permitAll()
//			.and()
//			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/signout")).logoutSuccessUrl("/signin");
	}
	
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(this.accountDetailsService);
	
		return daoAuthenticationProvider;
	}
	
	@Bean
	protected PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

}
