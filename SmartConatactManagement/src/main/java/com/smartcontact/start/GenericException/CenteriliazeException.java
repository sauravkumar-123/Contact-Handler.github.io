package com.smartcontact.start.GenericException;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CenteriliazeException {

	//Handling Exception for All Controller.
	
		@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
		@ExceptionHandler(value=Exception.class)
		public String GenericexceptionHandler(Model model)
		{
			model.addAttribute("msg","Exception found!!!!!!!");
			model.addAttribute("title","Error!! - Smart Contact Management");
			return "errorPage";
		}
}
