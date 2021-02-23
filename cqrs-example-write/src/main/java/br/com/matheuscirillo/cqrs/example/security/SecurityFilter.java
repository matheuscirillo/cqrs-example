package br.com.matheuscirillo.cqrs.example.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.matheuscirillo.cqrs.example.infrastructure.repository.TokenRepository;

public class SecurityFilter extends OncePerRequestFilter {

    private TokenRepository tokenRepository;

    public SecurityFilter(TokenRepository tokenRepository) {
	this.tokenRepository = tokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	    throws IOException, ServletException {
	String header = request.getHeader("Authorization");
	if (header == null || !header.startsWith("Bearer ")) {
	    chain.doFilter(request, response);
	    return;
	}

	Integer user = Integer.parseInt(JWT.require(Algorithm.HMAC512("EnfA3Rhdep".getBytes())).build()
		.verify(header.replace("Bearer ", "")).getSubject());

	Authentication authentication = null;
	if (tokenRepository.get(user).equals(header.replace("Bearer ", ""))) {
	    authentication = new Authentication() {

		@Override
		public String getName() {
		    return null;
		}

		@Override
		public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		}

		@Override
		public boolean isAuthenticated() {
		    return true;
		}

		@Override
		public Object getPrincipal() {
		    return user;
		}

		@Override
		public Object getDetails() {
		    return null;
		}

		@Override
		public Object getCredentials() {
		    return null;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
		    return null;
		}
	    };
	} else {
	    response.setStatus(401);
	}

	SecurityContextHolder.getContext().setAuthentication(authentication);
	chain.doFilter(request, response);
    }

}