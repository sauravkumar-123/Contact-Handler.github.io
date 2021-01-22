package com.smartcontact.start.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.smartcontact.start.DAO.ContactRepository;
import com.smartcontact.start.DAO.UserRepository;
import com.smartcontact.start.entities.Contact;
import com.smartcontact.start.entities.User;

@RestController
public class SearchController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	//Search Handler
	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query,Principal principal){
		System.out.println("Query=>"+query);
		User user=this.userRepository.getUserByUserName(principal.getName());
		List<Contact> contactslist=this.contactRepository.findByWorkContainingAndUser(query, user);
		return ResponseEntity.ok(contactslist);
	}

}
