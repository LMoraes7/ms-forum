package br.com.forum.security.filter;

import br.com.forum.domain.service.UserService;
import br.com.forum.security.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public final class AuthenticationViaTokenFilter extends OncePerRequestFilter {

	private static final String classNameLogger = "[" + AuthenticationViaTokenFilter.class.getSimpleName() + "] ";
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationViaTokenFilter.class);

	private final TokenService tokenService;
	private final UserService service;
	
	public AuthenticationViaTokenFilter(final TokenService tokenService, final UserService service) {
		this.tokenService = tokenService;
		this.service = service;
	}

	@Override
	protected void doFilterInternal(
			final HttpServletRequest request,
			final HttpServletResponse response,
			final FilterChain filterChain
	) throws ServletException, IOException {
		logger.info(classNameLogger + "starting user authentication process");
		final var token = retrieveToken(request);
		final boolean isValid = tokenService.isTokenValid(token);

		if (isValid)
			authenticateClient(token);

		logger.info(classNameLogger + "finalizing user authentication process");
		logger.info(classNameLogger + "dispatching requisition");
		filterChain.doFilter(request, response);
	}

	private void authenticateClient(final String token) {
		final var userEmail = tokenService.getUserEmail(token);
		logger.info(classNameLogger + "starting user authentication process of email {}", userEmail);
		final var authentication = new UsernamePasswordAuthenticationToken(
				service.findByEmail(userEmail),
				null,
				List.of()
		);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		logger.info(classNameLogger + "finalizing user authentication process of email {}", userEmail);
	}

	private String retrieveToken(final HttpServletRequest request) {
		logger.info(classNameLogger + "fetching data from Authorization header");
		final var token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			logger.info(classNameLogger + "Authorization header is invalid or does not exist in the request");
			return null;
		}

		logger.info(classNameLogger + "returning token contained in Authorization header");
		return token.substring(7, token.length());
	}

}
