package com.smartcontact.start.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.criterion.AggregateProjection;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "CONTACT")
public class Contact {
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int contact_id;
	
	@NotBlank(message = "Name not be empty")
	@NotNull(message = "Name should not be null")
	@Size(min=2,max=50,message = "minimum 2 and maximum 50 characters are allowed!!")
	private String contact_name;
	
	
	@Size(min=2,max=50,message = "minimum 2 and maximum 50 characters are allowed!!")
	private String contact_nickname;
	
	//@NotBlank(message = "Work field not be empty")
	//@NotNull(message = "Work field should not be null")
	private String work;
	
	@Column(unique = true)
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid Email")
	@NotNull(message = "Contact Email should not be null")
	@Size(min=2,max=50,message = "minimum 2 and maximum 50 characters are allowed!!")
	private String contact_email;
	
	@Column(unique = true)
	@Pattern(regexp = "(0/91)?[7-9][0-9]{9}", message = "Invalid Mobile number")
	@NotNull(message = "Contact Mobile number should not be null")
	@NotBlank(message = "Contact Mobile number should not be null")
	private String contact_mobno;
	
	//@NotNull(message = "Please select an image")
	//@NotBlank(message = "Please select an image")
	private String contact_imageurl;
	
	@Column(length = 1000)
	private String contact_description;
	
	@ManyToOne
	private User user;
	
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Contact(int contact_id, String contact_name, String contact_nickname, String work, String contact_email,
			String contact_mobno, String contact_imageurl, String contact_description, User user) {
		super();
		this.contact_id = contact_id;
		this.contact_name = contact_name;
		this.contact_nickname = contact_nickname;
		this.work = work;
		this.contact_email = contact_email;
		this.contact_mobno = contact_mobno;
		this.contact_imageurl = contact_imageurl;
		this.contact_description = contact_description;
		this.user = user;
	}

	public int getContact_id() {
		return contact_id;
	}

	public void setContact_id(int contact_id) {
		this.contact_id = contact_id;
	}

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getContact_nickname() {
		return contact_nickname;
	}

	public void setContact_nickname(String contact_nickname) {
		this.contact_nickname = contact_nickname;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getContact_email() {
		return contact_email;
	}

	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}

	public String getContact_mobno() {
		return contact_mobno;
	}

	public void setContact_mobno(String contact_mobno) {
		this.contact_mobno = contact_mobno;
	}

	public String getContact_imageurl() {
		return contact_imageurl;
	}

	public void setContact_imageurl(String contact_imageurl) {
		this.contact_imageurl = contact_imageurl;
	}

	public String getContact_description() {
		return contact_description;
	}

	public void setContact_description(String contact_description) {
		this.contact_description = contact_description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	//call when user use to delete or  the contact.
	@Override
	public boolean equals(Object obj)
	{
	   return this.contact_id==((Contact)obj).getContact_id();	
	}
	
	/*@Override
	public String toString() {
		return "Contact [contact_id=" + contact_id + ", contact_name=" + contact_name + ", contact_nickname="
				+ contact_nickname + ", work=" + work + ", contact_email=" + contact_email + ", contact_mobno="
				+ contact_mobno + ", contact_imageurl=" + contact_imageurl + ", contact_description="
				+ contact_description + ", user=" + user + "]";
	}*/

}
