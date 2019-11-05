package com.fileuploaddownload.filemgtdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fileuploaddownload.filemgtdemo.repository.RoleRepository;

@SpringBootApplication
public class FilemgtdemoApplication implements CommandLineRunner{
	
	 @Autowired
	 RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(FilemgtdemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		// save a few roles
		Role admin = new Role();
		admin.setId(3L);
		admin.setName(RoleName.ROLE_ADMIN);
		roleRepository.save(admin);

		Role manager = new Role();
		manager.setId(2L);
		manager.setName(RoleName.ROLE_PM);
		roleRepository.save(manager);

		Role user = new Role();
		user.setId(2L);
		user.setName(RoleName.ROLE_USER);
		roleRepository.save(user);
		*/
		
	}

}
