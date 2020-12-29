package com.smartcontact.start.DAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartcontact.start.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
	
	//Pagenation start
	
	//Pagebale interface has two information
	//(1)-> Currentpage (2)-> ContactPerpage   5
	@Query( value = "from Contact as c where c.user.user_id =:user_id")
	public Page<Contact> findContactsByUser(@Param("user_id") int user_id,Pageable pageable);

}
