package com.app.FirstApp.domain.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.FirstApp.domain.Services.UserService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public SecurityConfig(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/* =================== change Url /login ====================*/
		CustumAuthenticationFilter custumAuthenticationFilter=new CustumAuthenticationFilter(authenticationManagerBean());
		custumAuthenticationFilter.setFilterProcessesUrl("/api/login");
		/* ==================== end change Url =======================*/
		
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/api/login","/api/token/refresh/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/users/**").hasAnyAuthority("consulter_users"); //authorize only role =role_User to pass requet GET :http://localhost:9098/api/users
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/users/save/**").hasAnyAuthority("ajouter_users"); //authorize only role =role_User to pass requet GET :http://localhost:9098/api/users/save
		http.authorizeRequests().anyRequest().authenticated();
		/* =============== our filtre =================== */
		http.addFilter(custumAuthenticationFilter);
		http.addFilterBefore(new AuthorisationFilter(), UsernamePasswordAuthenticationFilter.class);
		/* =============== end our filtre =============== */
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
