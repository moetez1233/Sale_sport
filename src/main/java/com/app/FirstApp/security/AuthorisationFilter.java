package com.app.FirstApp.security;

import com.app.FirstApp.domain.role.Role;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


public class AuthorisationFilter extends OncePerRequestFilter {

	/*
	 * ===================================== traitement d'autorisation selon path
	 * =================
	 */

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	

		
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
		httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
		httpServletResponse.setHeader("Access-Control-Allow-Credentials", "*");
		httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
		
		if (request.getServletPath().equals("/login") || request.getServletPath().equals("/api/token/refresh") || request.getServletPath().equals("/manage")) {

			filterChain.doFilter(request, response); // let user connect
		} else {
			

			String authorisationHeader = request.getHeader(AUTHORIZATION);
			System.out.println("token is : "+authorisationHeader);
			
			
			if (authorisationHeader != null && authorisationHeader.startsWith("Bearer ")) {
				try {
					
					System.out.println("Token valid");
					String token = authorisationHeader.substring("Bearer ".length());// we just need the token without ODM
					Algorithm algotithm = Algorithm.HMAC256("secret".getBytes());
					JWTVerifier verifier = JWT.require(algotithm).build();
					DecodedJWT decodedJWT = verifier.verify(token);
					String userName = decodedJWT.getSubject();
				
					Role[] rolesTest = decodedJWT.getClaim("roles").asArray(Role.class);
					
					Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
					
					for(int i=0;i<rolesTest.length;i++) {
						authorities.add(new SimpleGrantedAuthority(rolesTest[i].getName()));

					}
					System.out.println("authorities filter : "+authorities);

					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							userName, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					System.out.println("wsol1");
					filterChain.doFilter(request, response); // let user connect
					System.out.println("wsol2");

				} catch (Exception e) {
					System.out.println("error authorisation  : "+e.getMessage());
					response.setHeader("error ", e.getMessage());
			
					
					Map<String,String> error_Message=new HashMap<>();
					error_Message.put("error", e.getMessage());
		

					response.setContentType("APPLICATION_JSON_VALUE");
					new ObjectMapper().writeValue(response.getOutputStream(), error_Message);
				}

			}else {
				filterChain.doFilter(request, response); 

			}
		}

	}

}
