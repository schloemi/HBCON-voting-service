package de.heimbrauconvention.votingservice.controller;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.heimbrauconvention.votingservice.service.RatingItemService;
import de.heimbrauconvention.votingservice.utils.ZXingHelper;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private RatingItemService ratingItemService;

	@RequestMapping(value="/ratingItems", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		modelMap.put("ratingItems", ratingItemService.getAll());
		return "admin/rating-items";
	}
	
	//TODO: DAs muss über die COmpetition eingeschränkt werden

	@RequestMapping(value = "/ratingItems/{ratingItemId}/qrcode", method = RequestMethod.GET)
	public void qrcode(
			HttpServletResponse response,
			@PathVariable("ratingItemId") String ratingItemId 
			) throws Exception {
		response.setContentType("image/png");
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(ZXingHelper.getQRCodeImage(ratingItemId, 200, 200));
		outputStream.flush();
		outputStream.close();
	}

}
