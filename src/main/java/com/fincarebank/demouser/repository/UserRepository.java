package com.fincarebank.demouser.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fincarebank.demouser.model.UserDetails;

@Repository
public interface UserRepository extends JpaRepository<UserDetails,Long>
{
	public Optional<UserDetails> findByEmailIdAndPassword(String emailId,String password);
	public Optional<UserDetails> findByEmailId(String emailId);
}
