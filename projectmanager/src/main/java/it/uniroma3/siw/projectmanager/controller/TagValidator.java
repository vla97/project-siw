package it.uniroma3.siw.projectmanager.controller;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.projectmanager.model.Tag;


@Component
public class TagValidator implements Validator {
	
	@Override
	public void validate(Object o, Errors errors) {
		Tag tag = (Tag) o;
		String nome = tag.getNome().trim();
		String colore = tag.getColore().trim();
		String descrizione = tag.getDescrizione().trim();
		
		if (nome.isEmpty())
			errors.rejectValue("nome", "required");
		
		if (colore.isEmpty())
			errors.rejectValue("colore", "required");
		
		if (descrizione.isEmpty())
			errors.rejectValue("dercrizione", "required");
	}
	@Override
	public boolean supports(Class<?> clazz) {
		return Tag.class.equals(clazz);
	}

}
