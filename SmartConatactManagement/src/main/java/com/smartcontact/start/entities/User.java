package com.smartcontact.start.entities;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



@Entity
@Table(name="USER")
public class User {
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int user_id;
	
	@NotBlank(message = "Name not be empty")
	@NotNull(message = "Name should not be null")
	@Size(min=2,max=50,message = "minimum 2 and maximum 50 characters are allowed!!")
	private String user_name;
	
	@Column(unique = true)
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid Email")
	@NotNull(message = "Email should not be null")
	@Size(min=2,max=50,message = "minimum 2 and maximum 50 characters are allowed!!")
	private String user_email;
	
	/*@Pattern(regexp = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=])"
            + "(?=\\S+$).{8,50}$",message = "It contains at least 8 characters and at most 50 characters\n\n"+
	                                         "It contains at least one digit\n\n"+		                                                                                                
	        		                          "It contains at least one upper case alphabet\n\n"+		                                                                                               
	                                          "It contains at least one lower case alphabet\n\n"+		                                                                                                
	        		                          "It contains at least one special character which includes !@#$%&*()-+=^ \n\n"+		                                                                                               
	                                           "It doesn’t contain any white space \n\n")*/
	@NotNull(message = "Password should not be null")
	@NotBlank(message = "Password should not be empty")
	private String user_password;
	private String user_role;
	private boolean isenabled;
	private String imageurl;
	@Column(length = 500)
	private String about_user;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy ="user")
	private List<Contact> contacts=new ArrayList<>();
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int user_id, String user_name, String user_email, String user_password, String user_role,
			boolean isenabled, String imageurl, String about_user, List<Contact> contacts) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_email = user_email;
		this.user_password = user_password;
		this.user_role = user_role;
		this.isenabled = isenabled;
		this.imageurl = imageurl;
		this.about_user = about_user;
		this.contacts = contacts;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}

	public boolean isIsenabled() {
		return isenabled;
	}

	public void setIsenabled(boolean isenabled) {
		this.isenabled = isenabled;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public String getAbout_user() {
		return about_user;
	}

	public void setAbout_user(String about_user) {
		this.about_user = about_user;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_name=" + user_name + ", user_email=" + user_email
				+ ", user_password=" + user_password + ", user_role=" + user_role + ", isenabled=" + isenabled
				+ ", imageurl=" + imageurl + ", about_user=" + about_user + ", contacts=" + contacts + "]";
	}

	
}
