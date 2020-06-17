package it.uniroma3.siw.projectmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.projectmanager.controller.session.SessionData;
import it.uniroma3.siw.projectmanager.model.User;
import it.uniroma3.siw.projectmanager.service.ProjectService;
import it.uniroma3.siw.projectmanager.service.UserService;
import static it.uniroma3.siw.projectmanager.model.Credenziali.ADMIN_ROLE;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

@Controller
public class MainController {

	@Autowired
	SessionData sessionData;

	public MainController() {
	}

	@GetMapping(value = { "/", "/index" })
	public String Index(Model model) {
		return "index";
	}

	@GetMapping(value = { "/home" })
	public String home(Model model) {
		User user = sessionData.getLoggedUser();
		model.addAttribute("user", user); 
		return "home";
	}

	@GetMapping(value = { "/tornaIndietro" })
	public void tornaIndietro(Model model, HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

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
