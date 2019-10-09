package com.james.training.jwtsecurity.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.james.training.jwtsecurity.message.request.ForgotPasswordForm;
import com.james.training.jwtsecurity.message.request.LoginForm;
import com.james.training.jwtsecurity.message.request.SignUpForm;
import com.james.training.jwtsecurity.message.request.UpdateUserInfoForm;
import com.james.training.jwtsecurity.message.response.JwtResponse;
import com.james.training.jwtsecurity.message.response.ResponseMessage;
import com.james.training.jwtsecurity.model.Role;
import com.james.training.jwtsecurity.model.RoleName;
import com.james.training.jwtsecurity.model.User;
import com.james.training.jwtsecurity.repository.RoleRepository;
import com.james.training.jwtsecurity.repository.UserRepository;
import com.james.training.jwtsecurity.security.jwt.JwtProvider;
import com.james.training.jwtsecurity.utility.MailConstructor;
import com.james.training.jwtsecurity.utility.SecurityUtility;
 
/*
 * AuthRestAPIs defines 2 APIs:

	/api/auth/signup: sign up
		-> check username/email is already in use.
		-> create User object
		-> store to database
		
	/api/auth/signin: sign in
		-> attempt to authenticate with AuthenticationManager bean.
		-> add authentication object to SecurityContextHolder
-		> Generate JWT token, then return JWT to client
 */
 
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {
 
  @Autowired
  AuthenticationManager authenticationManager;
 
  @Autowired
  UserRepository userRepository;
 
  @Autowired
  RoleRepository roleRepository;
 
  @Autowired
  PasswordEncoder encoder;
 
  @Autowired
  JwtProvider jwtProvider;
  
  @Autowired
  private MailConstructor mailConstructor;

	@Autowired
	private JavaMailSender mailSender;
 
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
 
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
 
    SecurityContextHolder.getContext().setAuthentication(authentication);
 
    String jwt = jwtProvider.generateJwtToken(authentication);
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
 
    return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
  }
  
 
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
	  
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
          HttpStatus.BAD_REQUEST);
    }
 
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
          HttpStatus.BAD_REQUEST);
    }
 
    // Creating user's account
    User user = new User(signUpRequest.getFirstname(),signUpRequest.getLastname(), signUpRequest.getUsername(), signUpRequest.getEmail(),
        encoder.encode(signUpRequest.getPassword()));
 
    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();
 
    strRoles.forEach(role -> {
      switch (role) {
      case "admin":
        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not found."));
        roles.add(adminRole);
 
        break;
      case "pm":
        Role pmRole = roleRepository.findByName(RoleName.ROLE_PM)
            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not found."));
        roles.add(pmRole);
 
        break;
      default:
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not found."));
        roles.add(userRole);
      }
    });
 
    user.setRoles(roles);
    userRepository.save(user);
    
    SimpleMailMessage email = mailConstructor.constructNewUserEmail(user, signUpRequest.getPassword());
	mailSender.send(email);
 
    return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
  }
  
  @CrossOrigin(origins = "*", maxAge = 3600)
  @PostMapping("/forgetPassword")
  public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordForm forgotPasswordRequest){
	 
	 User user = new User();
	 
	 user = userRepository.findByEmail(forgotPasswordRequest.getEmail());
	  
    if (user == null) {
    	
      return new ResponseEntity<>(new ResponseMessage("Email not found!"),
          HttpStatus.BAD_REQUEST);
    }
    
    String newPassword = SecurityUtility.randomPassword();
    
    String encryptedPassword =  encoder.encode(newPassword);
	user.setPassword(encryptedPassword);
	
	 userRepository.save(user);
    
    SimpleMailMessage newEmail = mailConstructor.constructNewUserEmail(user, newPassword);
	mailSender.send(newEmail);
 
    return new ResponseEntity<>(new ResponseMessage("New password generated successfully - Email sent!"), HttpStatus.OK);
  }
  
  @GetMapping("/getCurrentUser")
	public User getCurrentUser( Principal principal){
		
		User user = userRepository.findByUsername(principal.getName());
		
		return user;
	}
  
  @PostMapping("/updateInfo")
  public ResponseEntity<?> profileInfo(@Valid @RequestBody UpdateUserInfoForm updateUserInfo) throws Exception{
	
	Long id = (Long) updateUserInfo.getId();
	
	String email = (String) updateUserInfo.getEmail();
	String username = (String) updateUserInfo.getUsername();
	String firstName = (String) updateUserInfo.getFirstname();
	String lastName = (String) updateUserInfo.getLastname();
	String newPassword = (String) updateUserInfo.getNewPassword();
	String currentPassword = (String) updateUserInfo.getPassword();
	
	Optional<User> currentUser = userRepository.findById(Long.valueOf(id));
	
	if(currentUser == null) {
		throw new Exception ("User not found");
	}
	
	if(userRepository.findByEmail(email) != null) {
		if(userRepository.findByEmail(email).getId() != currentUser.get().getId()) {
			
			return new ResponseEntity<>("Email not found!", HttpStatus.BAD_REQUEST);
		}
	}
	
	if(userRepository.findByUsername(username) != null) {
		if(userRepository.findByUsername(username).getId() != currentUser.get().getId()) {
			return new ResponseEntity<>("Username not found!", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	 
		String dbPassword = currentUser.get().getPassword();
		
		if(null != currentPassword)
		if(encoder.matches(currentPassword, dbPassword)) {
			if(newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")) {
				
				 String encryptedPassword =  encoder.encode(newPassword);
				 
				currentUser.get().setPassword(encryptedPassword);
				
				currentUser.get().setPassword(encoder.encode(newPassword));
			}
			currentUser.get().setEmail(email);
		} else {
			return new ResponseEntity<>("Incorrect current password!", HttpStatus.BAD_REQUEST);
		}
	
	
	currentUser.get().setFirstname(firstName);
	currentUser.get().setLastname(lastName);
	currentUser.get().setUsername(username);
	currentUser.get().setEmail(email);
	
	
	userRepository.save(currentUser);
	
	// SimpleMailMessage newEmail = mailConstructor.constructNewUserEmail(user, newPassword);
	// mailSender.send(newEmail);
	
	return new ResponseEntity<>("Update Success", HttpStatus.OK);
}

}