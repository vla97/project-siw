package it.uniroma3.siw.projectmanager.autenticazione;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import static it.uniroma3.siw.projectmanager.model.Credenziali.ADMIN_ROLE;


@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_OK);

		boolean admin = false;

		
 
		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if (ADMIN_ROLE.equals(auth.getAuthority())) {
				admin = true;
			}
		}

		if (admin) {
			response.sendRedirect("/admin");
		} else {
			response.sendRedirect("/home");
		}
	}
}
