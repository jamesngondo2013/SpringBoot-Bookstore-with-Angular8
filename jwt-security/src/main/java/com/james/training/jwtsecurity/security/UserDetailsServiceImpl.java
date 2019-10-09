package com.james.training.jwtsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.james.training.jwtsecurity.model.User;
import com.james.training.jwtsecurity.repository.UserRepository;
 
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
 
  @Autowired
  UserRepository userRepository;
 
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
 
	  User user = userRepository.findByUsername(username);
	  
	  if(user == null) {
			throw  new UsernameNotFoundException("User Not Found with -> username or email : " + username);
		}
	  //.orElseThrow(
        //() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
 
    return UserPrinciple.build(user);
  }
}
