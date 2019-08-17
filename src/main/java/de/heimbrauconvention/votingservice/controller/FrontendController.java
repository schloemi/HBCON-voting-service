package de.heimbrauconvention.votingservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {

	@RequestMapping("/")
    public String homePage(Model model) {
       // model.addAttribute("appName", appName);
        return "index";
    }

}
