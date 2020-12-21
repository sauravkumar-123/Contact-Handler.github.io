package com.smartcontact.start.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartcontact.start.entities.User;

public interface UserRepository extends JpaRepository<User,Integer> {
	
	@Query(value = "select u from User u where u.user_email =:user_email")
	public User getUserByUserName(@Param("user_email") String user_email);
}
