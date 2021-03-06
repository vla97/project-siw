package it.uniroma3.siw.projectmanager.controller;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.projectmanager.model.Task;

@Component
public class TaskValidator implements Validator {

	final Integer MAX_NAME_LENGTH = 100;
	final Integer MIN_NAME_LENGTH = 2;

	@Override
	public void validate(Object o, Errors errors) {
		Task task = (Task) o;
		String name = task.getNome().trim();
		String descrizione = task.getDescrizione().trim();

		if (name.isEmpty())
			errors.rejectValue("nome", "required");

		if (descrizione.isEmpty())
			errors.rejectValue("descrizione", "required");

	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Task.class.equals(clazz);

	}

}
