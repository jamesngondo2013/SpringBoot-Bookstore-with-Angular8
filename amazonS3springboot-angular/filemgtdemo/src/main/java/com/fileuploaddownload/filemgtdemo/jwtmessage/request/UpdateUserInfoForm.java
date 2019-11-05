package com.fileuploaddownload.filemgtdemo.jwtmessage.request;

import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UpdateUserInfoForm {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		long id; 
		
	 	public long getId() {
			return id;
		}

	
	    @Size(min = 3, max = 50)
	    private String firstname;
	    
	    @Size(min = 3, max = 50)
	    private String lastname;
	 
	    @Size(min = 3, max = 50)
	    private String username;
	 
	    @Size(max = 60)
	    @Email
	    private String email;
	    
	    private Set<String> role;
	    
		@NotBlank
	    @Size(min = 6, max = 40)
	    private String password;
	    
	    
	    @Size(min = 6, max = 40)
	    private String newPassword;
	 
	    public String getFirstname() {
			return firstname;
		}

		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}

		public String getLastname() {
			return lastname;
		}

		public void setLastname(String lastname) {
			this.lastname = lastname;
		}

		public String getUsername() {
	        return username;
	    }
	 
	    public void setUsername(String username) {
	        this.username = username;
	    }
	 
	    public String getEmail() {
	        return email;
	    }
	 
	    public void setEmail(String email) {
	        this.email = email;
	    }
	 
	    public String getPassword() {
	        return password;
	    }
	 
	    public void setPassword(String password) {
	        this.password = password;
	    }
	    
	    public String getNewPassword() {
			return newPassword;
		}

		public void setNewPassword(String newPassword) {
			this.newPassword = newPassword;
		}

		public Set<String> getRole() {
	      return this.role;
	    }
	    
	    public void setRole(Set<String> role) {
	      this.role = role;
	    }
}
