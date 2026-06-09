package com.travelbuddy.security.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.travelbuddy.security.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	public JwtAuthenticationFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");

		System.out.println(">>> TB - URL chiamato: " + request.getRequestURI());
		System.out.println(">>> TB - header Authorization presente? " + (authHeader != null));

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			System.out.println(">>> TB - NESSUN Bearer token, passo oltre senza autenticare");
			filterChain.doFilter(request, response);
			return;
		}

		String token = authHeader.substring(7).trim();

		boolean valido = jwtService.isTokenValid(token);
		System.out.println(">>> TB - token valido? " + valido);

		if (!valido) {
			filterChain.doFilter(request, response);
			return;
		}

		String email = jwtService.extractEmail(token);
		Long authUserId = jwtService.extractAuthUserId(token);

		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			List<String> roles = jwtService.extractRoles(token);

			System.out.println(">>> TB - email: " + email + " | ruoli letti dal token: " + roles);

			List<GrantedAuthority> authorities = roles.stream()
					.map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role))
					.toList();

			System.out.println(">>> TB - authorities costruite: " + authorities);

			UsernamePasswordAuthenticationToken authToken =
					new UsernamePasswordAuthenticationToken(
							email,
							null,
							authorities
					);
			authToken.setDetails(authUserId);
			SecurityContextHolder.getContext().setAuthentication(authToken);
		}

		filterChain.doFilter(request, response);
	}
}