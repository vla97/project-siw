package it.uniroma3.siw.projectmanager.controller.session;

import it.uniroma3.siw.projectmanager.model.Credenziali;
import it.uniroma3.siw.projectmanager.model.User;
import it.uniroma3.siw.projectmanager.repository.CredenzialiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {

	private User user;

	private Credenziali credenziali;

	@Autowired
	private CredenzialiRepository credenzialiRepository;

	public Credenziali getLoggedCredenziali() {
		if (this.credenziali == null)
			this.update();
		return this.credenziali;
	}

	public User getLoggedUser() {
		if (this.user == null)
			this.update();
		return this.user;
	}

	private void update() {
		UserDetails loggedUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		this.credenziali = this.credenzialiRepository.findByUsername(loggedUserDetails.getUsername()).get();

		this.credenziali.setPassword("[PROTECTED]");
		this.user = this.credenziali.getUser();
	}

	public Credenziali getCredenzialiCorrenti() {
		return credenziali;
	}

	public void clear() {
		this.credenziali = null;
		this.user = null; 
	}

}