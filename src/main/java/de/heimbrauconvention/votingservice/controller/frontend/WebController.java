package de.heimbrauconvention.votingservice.controller.frontend;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.heimbrauconvention.votingservice.domain.Competition;
import de.heimbrauconvention.votingservice.service.CompetitionService;
import de.heimbrauconvention.votingservice.utils.ZXingHelper;

@Controller
@RequestMapping("/web")
public class WebController {

	@Autowired
	CompetitionService competitionService;
	

	@RequestMapping("/")
	public String homePage() {
		return "redirect:/web/dashboard";
	}

	@RequestMapping("/dashboard")
	public String dashboard() {
		return "/web/dashboard";
	}
			
			
	
	@RequestMapping("/competitions/{competitionId}")
	public String competitions(
    		HttpServletRequest request,
			HttpServletResponse response,
			Model model,
    		@PathVariable String competitionId) {
		
		Competition competition = competitionService.getByPublicId(competitionId);
		if (competition == null) {
			return "error";
		}
		model.addAttribute("competition", competitionService.convertToDto(competition));
		return "/web/competition";
	}

	
	@RequestMapping(value = "/utils/qrcode/{anyString}", method = RequestMethod.GET)
	public void qrcode(
			HttpServletResponse response,
			@PathVariable("anyString") String anyString 
		) throws Exception {
		
		response.setContentType("image/png");
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(ZXingHelper.getQRCodeImage(anyString, 200, 200));
		outputStream.flush();
		outputStream.close();
	}
}
