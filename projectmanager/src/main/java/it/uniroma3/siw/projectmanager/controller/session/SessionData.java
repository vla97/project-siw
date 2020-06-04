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

/**
 * SessionData is an interface to save and retrieve specific objects from the current Session.
 * It is mainly used to store the currently logged User and her Credentials
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {

    /**
     * Currently logged User
     */
    private User user;

    /**
     * Credentials for the currently logged User
     */
    private Credenziali credenziali;

    @Autowired
    private CredenzialiRepository credenzialiRepository;

    /**
     * Retrieve from Session the credentials for the currently logged user.
     * If they are not stored in Session already, retrieve them from the SecurityContext and from the DB
     * and store them in session.
     *
     * @return the retrieved Credentials for the currently logged user
     */
    public Credenziali getLoggedCredenziali() {
        if (this.credenziali == null)
            this.update();
        return this.credenziali;
    }

    /**
     * Retrieve from Session the currently logged User.
     * If it is not stored in Session already, retrieve it from the DB and store it in session.
     *
     * @return the retrieved Credentials for the currently logged user
     */
    public User getLoggedUser() {
        if (this.user == null)
            this.update();
        return this.user;
    }

    /**
     * Store the Credentials and User objects for the currently logged user in Session
     */
    private void update() {
        UserDetails loggedUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.credenziali = this.credenzialiRepository.findByUsername(loggedUserDetails.getUsername()).get(); // can never be absent
        this.credenziali.setPassword("[PROTECTED]");
        this.user = this.credenziali.getUser();
    }
}