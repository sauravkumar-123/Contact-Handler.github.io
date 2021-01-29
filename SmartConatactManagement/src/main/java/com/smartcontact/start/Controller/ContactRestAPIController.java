package com.smartcontact.start.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smartcontact.start.ContactAPIservice.ContactService;
import com.smartcontact.start.ContactAPIservice.ContatsImageUpload;
import com.smartcontact.start.entities.Contact;

@RestController
public class ContactRestAPIController {

	@Autowired
	private ContactService contactService;
	
	@Autowired
	private ContatsImageUpload ContatsImageUpload;
	
	
	
	
	//geeting all contact Handler.
	@GetMapping("/allcontacts")
	//public List<Contact> getAllContacts() {
	public ResponseEntity<List<Contact>> getAllContacts(){
	  List<Contact> contactlist=this.contactService.getAllContacts();
		
		if(contactlist.size()<=0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		   //return ResponseEntity.of(Optional.of(contactlist));
		  return ResponseEntity.status(HttpStatus.CREATED).body(contactlist);
	}
	
	
	
	
	
	//geeting Single Contact Handler.
	@GetMapping("/singlecontact/{contactid}")
	//public Contact getSingleContact(@PathVariable("contactid") int contactid)
	public ResponseEntity<Contact> getSingleContact(@PathVariable("contactid") int contactid)
	{
		Contact contact=this.contactService.getContactByID(contactid);
		if(contact==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(contact));
	}
	
	
	
	
	
	//add contact to list Handler.
	@PostMapping("/allcontacts")
	//public Contact CreateContact(@RequestBody Contact contact)
	public ResponseEntity<Contact> CreateContact(@RequestBody Contact contact)
	{
		Contact contactinfo=null;
		try {
			contactinfo=this.contactService.addContacts(contact);
			System.out.println("get Contacts===>"+contactinfo);
			return ResponseEntity.of(Optional.of(contactinfo));
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Internal Server Error found!!!!!!!");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	
	
	
	
	//delete book Handler.
	@DeleteMapping("/allcontacts/{contactid}")
	//public void DeleteConatct(@PathVariable("contactid") int contactid)
	public ResponseEntity<?> DeleteConatct(@PathVariable("contactid") int contactid)
	{
		try {
			this.contactService.deleteContact(contactid);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	

	//update Contact Handler
	@PutMapping("/allcontacts/{contactid}")
	//public Contact UpdateContact(@RequestBody Contact contact,@PathVariable("contactid") int contactid)
	public ResponseEntity<Contact> UpdateContact(@RequestBody Contact contact,@PathVariable("contactid") int contactid)
	{
		try {
		this.contactService.updateContact(contact,contactid);
		return ResponseEntity.ok().body(contact);
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	}
	
	
	//File upload controller
	@PostMapping("/contactsuploadfile")
	public ResponseEntity<String> ContactsImageupload(@RequestParam("Profileimage") MultipartFile file) throws IOException{
	   /* System.out.println("Image file data==>"+file.getOriginalFilename());
		System.out.println("Image file data size==>"+file.getSize());
		System.out.println("Image file data contenttype==>"+file.getContentType());
		System.out.println("Image file data name==>"+file.getName());
		System.out.println("Image file data bytesdata==>"+file.getBytes());
		System.out.println("Image file data resource==>"+file.getResource());*/

		
		try {
			
		if(file.isEmpty()==true) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Resource file not found!!!!");
		}
		
		if(file.getContentType().equals("img/pdf")) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Resource file in pdf format!!!!");
			
		}
		//file upload code
		
		Boolean flagBoolean=this.ContatsImageUpload.uploadCheck(file);
		
		if(flagBoolean)
		{
			//return ResponseEntity.ok("File uploaded Successfully!!!!");
			
			return ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentContextPath().path("/img/").path(file.getOriginalFilename()).toUriString());
		}
		
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!!Try again");
	}
}
