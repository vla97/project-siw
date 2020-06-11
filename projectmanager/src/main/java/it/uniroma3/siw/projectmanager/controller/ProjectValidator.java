package it.uniroma3.siw.projectmanager.controller;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.projectmanager.model.Project;

@Component
public class ProjectValidator implements Validator {

	final Integer MAX_NAME_LENGTH = 100;
	final Integer MIN_NAME_LENGTH = 2;


	@Override
	public void validate(Object o, Errors errors) {
		Project project  = (Project) o;
		String nome = project.getName().trim();
		
		if (nome.isEmpty())
			errors.rejectValue("name", "required");
		else if (nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
			errors.rejectValue("name", "size");


	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Project.class.equals(clazz);

	}

}
