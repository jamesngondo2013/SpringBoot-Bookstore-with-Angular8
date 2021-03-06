package com.james.training.jwtsecurity.utility;

import org.springframework.stereotype.Component;

import com.james.training.jwtsecurity.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;

@Component
public class MailConstructor {

	@Autowired
	private Environment env;
	
	public SimpleMailMessage constructNewUserEmail(User user, String password) {
		String message="\nPlease use the following credentials to log in and edit your personal information including your own password."
				+ "\nUsername:"+user.getUsername()+"\nPassword:"+password;
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("James' Bookstore - User Info");
		email.setText(message);
		email.setFrom(env.getProperty("keithchingotah@gmail.com"));
		return email;
	}
	
	public SimpleMailMessage constructUpdateUserDetails(User user, String newPassword) {
		String message="\nYou have updated your personal details. Please use the following credentials to login: "
				+ "\nUsername: "+user.getUsername()+"\nPassword: "+ newPassword+"\nEmail: "+user.getEmail();
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("James' Bookstore - Updated User Info");
		email.setText(message);
		email.setFrom(env.getProperty("keithchingotah@gmail.com"));
		return email;
	}

}
