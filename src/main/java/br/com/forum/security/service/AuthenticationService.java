package br.com.forum.security.service;

import br.com.forum.domain.exception.NotFoundException;
import br.com.forum.domain.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public final class AuthenticationService implements UserDetailsService {

	private final UserService service;

	public AuthenticationService(final UserService service) {
		this.service = service;
	}

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		try {
			return service.findByEmail(username);
		} catch (NotFoundException ex) {
			throw new UsernameNotFoundException("informed email does not exist!");
		}
	}
}
