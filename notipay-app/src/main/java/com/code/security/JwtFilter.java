package com.code.security;

import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.code.dao.UserDao;
import com.code.model.User;
import com.code.services.AccountDetails;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private JwtUtility jwtUtil;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(header == null || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		final String token = header.split(" ")[1].trim();
		
		User user = userDao.getUserByUsername(jwtUtil.getUsernameFromToken(token));
		AccountDetails accountDetails = new AccountDetails(user);
		
		if(!jwtUtil.validateToken(token, accountDetails)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				accountDetails, null,  
				accountDetails == null ? List.of() : accountDetails.getAuthorities());
		
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}	
}
