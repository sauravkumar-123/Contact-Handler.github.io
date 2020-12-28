package com.smartcontact.start.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.sql.CommonDataSource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smartcontact.start.DAO.UserRepository;
import com.smartcontact.start.entities.Contact;
import com.smartcontact.start.entities.User;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userrepository;

	//common data to be response.
	@ModelAttribute
	public void CommonData(Model model,Principal principal) {
		String username=principal.getName();
		System.out.println("Username===>"+username);
		
		User user=this.userrepository.getUserByUserName(username);
		//get the user using username.
		System.out.println("User==>"+user.toString());
		
		model.addAttribute("user",user);
	}
	
	
	//Dashboard home
	@RequestMapping("/index")
	public String dashboard(Model model,Principal principal)
	{
		/*
		String username=principal.getName();
		System.out.println("Username===>"+username);
		
		User user=this.userrepository.getUserByUserName(username);
		//get the user using username.
		System.out.println("User==>"+user.toString());
		
		model.addAttribute("user",user);*/
		model.addAttribute("title", "User Dashboard- Smart Contact Management");
		return "normal/user_dashboard";
	}
	
	
	
	//Add contact form handler
	@GetMapping("/add-contact")
	public String openAddContactForm(Model model) {
		model.addAttribute("title", "Add Contact - Smart Contact Management");
		model.addAttribute("contact",new Contact());
		return "normal/addcontactform";
	}
	
	//processing add contact form.
	@PostMapping("/process-addcontact")
	public String processContact(@ModelAttribute("contact") Contact contact,
			@RequestParam("profileimage") MultipartFile imagefile, 
			Principal principal) {
	
		try {
		
		String username=principal.getName();
		User user=this.userrepository.getUserByUserName(username);
		
		//processing and upload image file
		if(imagefile.isEmpty()) {
			System.out.println("file not recevied here");
		}
		else {
			
			//get file and upload it to folder.
			String filename=imagefile.getOriginalFilename();
			contact.setContact_imageurl(filename);
			File savefile=new ClassPathResource("static/contactimage").getFile();
			Path path=Paths.get(savefile.getAbsolutePath()+File.separator+imagefile.getOriginalFilename());
			Files.copy(imagefile.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
			
			System.out.println("filename");
		}
		
		user.getContacts().add(contact);
		contact.setUser(user);
		
		this.userrepository.save(user);
		
		System.out.println("Data==>"+contact.toString());
		System.out.println("add done");
			
	}
	catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		System.out.println("Error==>"+e.getMessage());
	}

return "normal/addcontactform";
  
}
	
}



