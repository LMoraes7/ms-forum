package br.com.forum.security.config;

import br.com.forum.security.filter.AuthenticationViaTokenFilter;
import br.com.forum.security.service.AuthenticationService;
import br.com.forum.security.service.TokenService;
import br.com.forum.domain.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@Configuration
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {

	private final AuthenticationService authenticationService;
	private final TokenService tokenService;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public SecurityWebConfig(
			final AuthenticationService authenticationService,
			final TokenService tokenService,
			final UserService userService,
			@Qualifier("bCryptPasswordEncoder") final PasswordEncoder passwordEncoder
	) {
		this.authenticationService = authenticationService;
		this.tokenService = tokenService;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationService).passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/user/login").permitAll()
			.antMatchers("/user/register").permitAll()
			.anyRequest().authenticated()
		.and().cors().and().csrf().disable()
		.sessionManagement().sessionCreationPolicy(STATELESS)
		.and().addFilterBefore(new AuthenticationViaTokenFilter(tokenService, userService), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
	}
	
}