package com.james.training.jwtsecurity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.james.training.jwtsecurity.repository.RoleRepository;

@SpringBootApplication
public class JwtSecurityApplication implements CommandLineRunner{

	 @Autowired
	 RoleRepository roleRepository;
	 
	public static void main(String[] args) {
		SpringApplication.run(JwtSecurityApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
	
		 
		
		
	}

}
