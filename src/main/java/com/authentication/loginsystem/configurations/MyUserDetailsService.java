package com.authentication.loginsystem.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.authentication.loginsystem.models.Users;
import com.authentication.loginsystem.repositories.UsersRepo;
@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private UsersRepo usersRepo;
	public String userName;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users=usersRepo.findByName(username);
		if(users==null)
		{
			throw new UsernameNotFoundException("User404");
		}
		userName=users.getName();
		return new UsersDetail(users);
	}
}
