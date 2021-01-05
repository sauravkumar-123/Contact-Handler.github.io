package com.smartcontact.start.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.servlet.http.HttpSession;
import javax.sql.CommonDataSource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smartcontact.start.DAO.ContactRepository;
import com.smartcontact.start.DAO.UserRepository;
import com.smartcontact.start.Helper.Message;
import com.smartcontact.start.entities.Contact;
import com.smartcontact.start.entities.User;



@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userrepository;
	
	@Autowired
	private ContactRepository contactrepository;

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
	public String processContact(@Valid @ModelAttribute("contact") Contact contact,
			BindingResult bindingresult,
			@RequestParam("profileimage") MultipartFile imagefile, 
			Model model,Principal principal,
			HttpSession session) 
	
	{
	
		try {
			
			
			if(bindingresult.hasErrors())
			{
				System.out.println("Error.......->"+bindingresult);
				model.addAttribute("contact", contact);
				
				throw new Exception();
				//session.setAttribute("message", new Message("Something went wrong !!","alert-danger"));
				//return "normal/addcontactform";
			}		
		
		String username=principal.getName();
		User user=this.userrepository.getUserByUserName(username);
		
		//processing and upload image file
		if(imagefile.isEmpty()) {
			System.out.println("file not recevied here");
			contact.setContact_imageurl("contact.png");
		}
		else {
			
			//get file and upload it to folder.
			String filename=imagefile.getOriginalFilename();
			contact.setContact_imageurl(filename);
			File savefile=new ClassPathResource("static/img").getFile();
			Path path=Paths.get(savefile.getAbsolutePath()+File.separator+imagefile.getOriginalFilename());
			Files.copy(imagefile.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
			
			System.out.println("filename");
		}
		
			
		
		user.getContacts().add(contact);
		contact.setUser(user);
		
		this.userrepository.save(user);
		
		System.out.println("Data==>"+contact);
		System.out.println("add done");
		
		model.addAttribute("contact", contact);
		
		session.setAttribute("message", new Message("Contact Added Sucessfully!!! Add More","alert-success"));
		return "normal/addcontactform";
			
	}
	catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		System.out.println("Error from catch==>"+e.getMessage());
		
		model.addAttribute("contact", contact);
		session.setAttribute("message", new Message("Something went wrong !! Try Again","alert-danger"));
		return "normal/addcontactform";
	}

}
	
 // View contact handler
//per page 5 contact allowed
// current page start =0
	
	@GetMapping("/show-contacts/{page}")
	public String ShowContacts(@PathVariable("page") Integer page,Model model,Principal principal) 
	{
		model.addAttribute("title", "Show Contacts - Smart Contact Management");
		
		//fetching contact detail.
		
		String username=principal.getName();
		User user=this.userrepository.getUserByUserName(username);
		
		//it has two information current page and Contact per page. 
		Pageable pageable=PageRequest.of(page, 10);
		Page<Contact> contactList = this.contactrepository.findContactsByUser(user.getUser_id(),pageable);
	
		/*List<Contact> contactList=user.getContacts();*/
		
		model.addAttribute("contacts", contactList);
		model.addAttribute("currentpage", page);
		model.addAttribute("totalpages", contactList.getTotalPages());
		return "normal/showcontacts";
	}
	
	
	//Showing particular contact detail.
	@GetMapping("/{contact_id}/contact")
	public String ParticularDetail(@PathVariable("contact_id") Integer cid,Model model,Principal principal) {
		
		System.out.println("CID==>"+cid);
		Optional<Contact> contactoptional=this.contactrepository.findById(cid);
		
		Contact contact=contactoptional.get();
		String username=principal.getName();
		User user=this.userrepository.getUserByUserName(username);
		
	try {
		if(user.getUser_id()==contact.getUser().getUser_id())
		{
			model.addAttribute("contact", contact);
			model.addAttribute("title",contact.getContact_name());
		}
		
	return "normal/specificcontactdetail";
	}catch(Exception e) 
	{
	e.printStackTrace();
	return "normal/specificcontactdetail";
	}
		
	}
	
	
	//delete a single contact handler.
	@GetMapping("/deletecontact/{contact_id}")
	public String DeleteContact(@PathVariable("contact_id") Integer cid,Model model,Principal principal,HttpSession session)
	{
		Optional<Contact> contactoptional=this.contactrepository.findById(cid);
		Contact contact=contactoptional.get();
		
		String username=principal.getName();
		User user=this.userrepository.getUserByUserName(username);
		
		//check
	
		try {
			if(user.getUser_id()==contact.getUser().getUser_id())
			{
			contact.setUser(null);
			this.contactrepository.delete(contact);
			session.setAttribute("message",new Message("Contact deleted Successfully","alert-success"));
			}
			return "redirect:/user/show-contacts/0";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "redirect:/user/show-contacts/0";
		}
		
	}
}



