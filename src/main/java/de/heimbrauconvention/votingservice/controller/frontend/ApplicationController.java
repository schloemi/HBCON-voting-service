package de.heimbrauconvention.votingservice.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationController {

	@RequestMapping("/")
	public String homePage() {
		return "redirect:/web/dashboard";
	}

}
