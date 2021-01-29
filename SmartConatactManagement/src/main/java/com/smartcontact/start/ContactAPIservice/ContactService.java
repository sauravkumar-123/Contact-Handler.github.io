package com.smartcontact.start.ContactAPIservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartcontact.start.DAO.ContactRepository;
import com.smartcontact.start.entities.Contact;

@Component
public class ContactService {

	@Autowired
	private ContactRepository contactRepository;
	
	/*
	private static List<Contact> contactslist=new ArrayList<Contact>();
	
	static {
      Contact contact1=new Contact();
		
		contact1.setContact_name("dheeraj kumar");
		contact1.setContact_nickname("Dheru");
		contact1.setContact_email("dheeraj.dk12@gmail.com");
		contact1.setContact_mobno("8574142451");
		
        Contact contact2=new Contact();
		
		contact2.setContact_name("Rajeev Singh");
		contact2.setContact_nickname("Raju");
		contact2.setContact_email("rajeevsigh76@gmail.com");
		contact2.setContact_mobno("9652411230");
		
       Contact contact3=new Contact();
		
		contact3.setContact_name("Pammi Rani");
		contact3.setContact_nickname("Pammi");
		contact3.setContact_email("pammikumari123@gmail.com");
		contact3.setContact_mobno("8574152496");
		
		contactslist.add(contact1);
		contactslist.add(contact2);
		contactslist.add(contact3);
	}*/
	
	//geeting all Contacts.
	public List<Contact> getAllContacts(){
		List<Contact> contactslist=this.contactRepository.findAll();
		return contactslist;
	}
	
	
	//geeting single Contact by contactID
	public Contact getContactByID(int contactid) {
		Contact contactinfo=null;
		
		 try {
			 //contactinfo=contactslist.stream().filter(data->data.getContact_id()==contactid).findFirst().get();
			 contactinfo=this.contactRepository.findById(contactid);
	    	  }
	       	  catch (Exception e) {
				// TODO: handle exception
	       		  e.printStackTrace();
	       		  System.out.println("No Contact found with this contactid!!!!");
			}
			  return contactinfo;
		
	}
	
	
	
	
	//add contact to list.
    public Contact addContacts(Contact contact)
    {
  	  //contactslist.add(contact);
    	Contact addedContact=this.contactRepository.save(contact);
  	  return addedContact;
    }
    
    
  
    //delete Contact 
    public void deleteContact(int contactid)
    {
  	  //contactslist=contactslist.stream().filter(data->data.getContact_id()!=contactid).collect(Collectors.toList());
  	  
  	  /*contactslist=contactslist.stream().filter(data->{
  		  if(data.getContact_id()!=contactid)
  		  {
  			  return true;
  		  }
  		  else {
  			  return false;
  		  }
  	  }).collect(Collectors.toList());*/
    	
    	this.contactRepository.deleteById(contactid);
  	  
    }
    
    
    
  //update contact  
    public void updateContact(Contact contact,int contactid)
    {
    	/*
  	  contactslist=contactslist.stream().map(c->{
  		  if(c.getContact_id()==contactid)
  		  {
  			  c.setContact_name(contact.getContact_name());
  			  c.setContact_nickname(contact.getContact_nickname());
  			  c.setContact_email(contact.getContact_email());
  			  c.setContact_mobno(contact.getContact_mobno());
  		  }
  		  return c;
  	  }).collect(Collectors.toList()); */
    	
    	contact.setContact_id(contactid);
    	this.contactRepository.save(contact);
    	
    }
    
}
