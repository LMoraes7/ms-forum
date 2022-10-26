package br.com.forum.config.security;

import br.com.forum.domain.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AuthenticationViaTokenFilter extends OncePerRequestFilter {

	private final TokenService tokenService;
	private final UserService service;
	
	public AuthenticationViaTokenFilter(final TokenService tokenService, final UserService service) {
		this.tokenService = tokenService;
		this.service = service;
	}

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
		final var token = retrieveToken(request);
		final boolean isValid = tokenService.isTokenValid(token);

		if (isValid)
			authenticateClient(token);

		filterChain.doFilter(request, response);
	}

	private void authenticateClient(String token) {
		final var userId = tokenService.getUserId(token);
		final var user = service.findById(userId);
		final var authentication = new UsernamePasswordAuthenticationToken(user, null, List.of());

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String retrieveToken(HttpServletRequest request) {
		final var token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7, token.length());
	}

}
