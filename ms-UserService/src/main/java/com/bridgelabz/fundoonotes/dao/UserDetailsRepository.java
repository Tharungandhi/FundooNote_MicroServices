package com.bridgelabz.fundoonotes.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.UserDetails;


@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer>{

	UserDetails getUserByEmailId(String emailId);

	Optional<UserDetails> findByEmailId(String emailId);

//	@Query(" SELECT user_details FROM FundooNote_MicroService")
//	List<UserDetails> findAlluser(String tabelName);

	
	

}
