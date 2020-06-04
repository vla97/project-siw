package it.uniroma3.siw.projectmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	
	public MainController() {
	}
	
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String Index(Model model) {
		return "index";
	}
}
