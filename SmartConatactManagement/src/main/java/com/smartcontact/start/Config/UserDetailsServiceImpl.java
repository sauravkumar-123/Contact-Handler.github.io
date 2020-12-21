package com.smartcontact.start.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smartcontact.start.DAO.UserRepository;
import com.smartcontact.start.entities.User;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userrepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// fetching user from database.
		User user=userrepository.getUserByUserName(username);
		
		if(user==null)
		{
			throw new UsernameNotFoundException("Couldn't find any user!!!!");
		}
		
		CustomUserDetails customuserdetails=new CustomUserDetails(user);
		
		return customuserdetails;
	}

}
