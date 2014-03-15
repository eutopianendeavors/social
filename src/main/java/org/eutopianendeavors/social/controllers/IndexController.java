package org.eutopianendeavors.social.controllers;

import org.apache.log4j.Logger;
import org.eutopianendeavors.social.models.FormData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	/*
	 * See this page for great examples of BindingResult and validation:
	 * http://codetutr.com/2013/05/28/spring-mvc-form-validation/
	 */
	static final Logger logger = Logger.getLogger(IndexController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(ModelMap model) {
		// model.addAttribute("helloworld", "Hello World");
		getMessage();
		return "index";
	}

	@ModelAttribute("helloworld")
	private String getMessage() {
		return "Hello World";
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public String test(@ModelAttribute("formdata") FormData formData) {
		logger.warn(formData.getTestData() + " was submitted.");
		return "redirect:";
	}

}
