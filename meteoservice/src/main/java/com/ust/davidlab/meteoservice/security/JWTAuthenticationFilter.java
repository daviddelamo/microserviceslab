package com.ust.davidlab.meteoservice.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import static com.ust.davidlab.meteoservice.security.SecurityConstants.HEADER_STRING;
import static com.ust.davidlab.meteoservice.security.SecurityConstants.TOKEN_PREFIX;

@Component
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

	@Value("${security.secret}")
	String secret;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(HEADER_STRING);

		if (HttpMethod.OPTIONS.toString().equals(req.getMethod())) {
			res.setStatus(HttpServletResponse.SC_OK);
			chain.doFilter(req, res);
		} else {

			if (header == null || !header.startsWith(TOKEN_PREFIX)) {
				res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}

			UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

			if (authentication == null) {
				res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}

			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(req, res);
		}
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			// parse the token.
			Jws<Claims> parseClaimsJws = Jwts.parser().setSigningKey(secret)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""));
			String user = parseClaimsJws.getBody().getSubject();

			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}
			return null;
		}
		return null;
	}
}
