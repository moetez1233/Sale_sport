package com.app.FirstApp.Security;

import com.app.FirstApp.services.userRole.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@Configuration
@EnableWebSecurity
/*@EnableGlobalMethodSecurity(
		prePostEnabled = true
	
)*/
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserService userDetailsService;

	private final PasswordEncoder bCryptPasswordEncoder;



	public SecurityConfig(UserService userDetailsService, PasswordEncoder bCryptPasswordEncoder) {
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
		//custumAuthenticationFilter.setFilterProcessesUrl("/api/login");
		/* ==================== end change Url =======================*/
		http.cors().disable();
		http.csrf().disable();

				/*.antMatcher("/**")
				.authorizeRequests().and()
				.httpBasic()
				.and()
				.authorizeRequests().anyRequest().authenticated().and().cors();*/
	
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/login","/api/token/refresh/**").permitAll();


		http.csrf().disable()
				.authorizeRequests().antMatchers(HttpMethod.GET,"/api/depot").hasAnyAuthority("ADMIN").and().cors(); //authorize only role =role_User to pass requet GET :http://localhost:9098/api/users


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
