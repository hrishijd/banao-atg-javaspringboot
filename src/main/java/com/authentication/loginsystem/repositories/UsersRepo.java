package com.authentication.loginsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authentication.loginsystem.models.Users;
@Repository
public interface UsersRepo extends JpaRepository<Users, Long>{
	Users findByName(String name);
}
