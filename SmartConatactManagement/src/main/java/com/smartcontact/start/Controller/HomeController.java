package com.smartcontact.start.Controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.smartcontact.start.DAO.UserRepository;
import com.smartcontact.start.Helper.Message;
import com.smartcontact.start.entities.User;



@Controller
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository UserRepository;
	
	//Homepage Handler
	@RequestMapping("/")
	public String home(Model model)
	{
		model.addAttribute("title", "Home - Smart Contact Management");
		return "home";
	}
	
	//About Page Handler
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About - Smart Contact Management");
		return "about";
	}
	
	//Register Page Handler
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Register - Smart Contact Management");
		model.addAttribute("user", new User());
		return "signup";
	}
	
	
	//Registring data handler.
	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user,
			BindingResult bindingresult,
			@RequestParam(value = "agreement" ,defaultValue = "false") boolean agreement,
			Model model,
			HttpSession session )
	{
		try {
			
		if(!agreement)
		{
			System.out.println("You have not agreed the terms and conditions");
			throw new Exception("You have not agreed the terms and conditions");
		}
		
		if(bindingresult.hasErrors())
		{
			System.out.println("Error==>"+bindingresult);
			model.addAttribute("user", user);
			return "signup";
		}
		
		user.setUser_role("ROLE_USER");
		user.setIsenabled(true);
		user.setImageurl("default.png");
		user.setUser_password(passwordEncoder.encode(user.getUser_password()));
		
		System.out.print("Agreement--->"+agreement);
		System.out.print("User----->"+user.toString());
		
		User resultUser=this.UserRepository.save(user);
		System.out.print("UserResult----->"+resultUser);
		
		model.addAttribute("user", user);
		session.setAttribute("message", new Message("Sucessfully Registered!!!","alert-success"));
		 return "signup";
		}
		catch (Exception e) {
			// TODO: handle exception
		 e.printStackTrace();
		 model.addAttribute("user", user);
		 session.setAttribute("message", new Message("Something went wrong !!"+e.getMessage(),"alert-danger"));
		 return "signup";
		}
		
	}
	
	//handler for custom login
     
	//@RequestMapping(value = "/signin", method = RequestMethod.GET)
	@GetMapping("/signin")
		public String customLogin(Model model)
		{
			model.addAttribute("title","Login Page - Smart Contact Management");
			return "login";
		}
	
	@GetMapping("/loginerror")
	public String customLoginFail(Model model)
	{
		model.addAttribute("title","Login Error!!! - Smart Contact Management");
		return "loginfail";
	}
}
