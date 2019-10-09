package com.james.training.jwtsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * TestRestAPIs define 3 RestAPIs:

	/api/test/user -> access by users has USER_ROLE or ADMIN_ROLE
	/api/test/pm -> access by users has USER_PM or ADMIN_ROLE
	/api/test/admin -> access by users has ADMIN_ROLE
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TestRestAPIs {
  
  @GetMapping("/api/test/user")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public String userAccess() {
    return ">>> User Contents!";
  }
 
  @GetMapping("/api/test/pm")
  @PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
  public String projectManagementAccess() {
    return ">>> Project Management Board";
  }
  
  @GetMapping("/api/test/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return ">>> Admin Contents";
  }
}