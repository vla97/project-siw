package it.uniroma3.siw.projectmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.projectmanager.model.Credenziali;
import it.uniroma3.siw.projectmanager.model.User;
import it.uniroma3.siw.projectmanager.service.CredenzialiService;


@Component
public class CredenzialiValidator implements Validator{
	
	@Autowired
	CredenzialiService credenzialiService;
	
	final Integer MAX_PU_LENGTH = 20;
	final Integer MIN_PU_LENGTH = 3;
	
	@Override
	public void validate(Object o, Errors errors) {
		Credenziali credenziali = (Credenziali) o;
		String username = credenziali.getUsername().trim();
		String password = credenziali.getPassword().trim();
		
		if (username.isEmpty())
			errors.rejectValue("username", "required");
		else if (username.length() < MIN_PU_LENGTH || username.length() > MAX_PU_LENGTH)
			errors.rejectValue("username", "size");
		else if (this.credenzialiService.getCredenziali(username)!= null)
			errors.rejectValue("username", "duplicate");
		
		if (password.isEmpty())
			errors.rejectValue("password", "required");
		else if (password.length() < MIN_PU_LENGTH || password.length() > MAX_PU_LENGTH)
			errors.rejectValue("password", "size");
	}
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}
}
