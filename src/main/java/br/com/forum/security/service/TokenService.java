package br.com.forum.security.service;

import br.com.forum.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public final class TokenService {

	private static final String classNameLogger = "[" + TokenService.class.getSimpleName() + "] ";
	private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

	private final String expiration;
	private final SecretKey securityKey;

	public TokenService(
			@Value("${forum.jwt.expiration}") final String expiration,
			@Qualifier("secretKey") final SecretKey securityKey
	) {
		this.expiration = expiration;
		this.securityKey = securityKey;
	}

	public String generateToken(final Authentication authentication) {
		final var user = (User) authentication.getPrincipal();
		logger.info(classNameLogger + "starting access token generation process for user of id {}", user.id());
		final var now = new Date();
		final var acessToken = Jwts.builder()
				.setIssuer("ms-forum")
				.setSubject(user.email())
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + Long.parseLong(expiration)))
				.signWith(securityKey)
				.addClaims(buildClaims(user))
				.compact();
		logger.info(classNameLogger + "finalizing access token generation process for user of id {}", user.id());
		return acessToken;
	}

	public boolean isTokenValid(final String token) {
		if (token == null)
			return false;

		boolean isValid = false;
		try {
			logger.info(classNameLogger + "starting access token validation process");
			decodeToken(token);
			isValid = true;
			logger.info(classNameLogger + "access token is valid");
		} catch (Exception e) {
			logger.info(classNameLogger + "access token is not valid");
		}
		logger.info(classNameLogger + "finalizing access token validation process");
		return isValid;
	}

	public String getUserEmail(final String token) {
		logger.info(classNameLogger + "starting user claims fetching process in access token");
		final var userEmail = decodeToken(token).getBody().getSubject();
		logger.info(classNameLogger + "finalizing user claims fetching process in access token");
		return userEmail;
	}

	private Jws<Claims> decodeToken(final String token) {
		return Jwts.parserBuilder().setSigningKey(securityKey).build().parseClaimsJws(token);
	}

	private Map<String, Object> buildClaims(User user) {
		final var claims = new HashMap<String, Object>();
		claims.put("id", user.id());
		claims.put("name", user.name());
		claims.put("email", user.email());
		claims.put("profile_picture", user.profilePicture());
		return claims;
	}

}