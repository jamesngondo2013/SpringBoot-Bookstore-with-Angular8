package com.bookstore.service;

import java.util.Set;

import com.bookstore.domain.security.UserRole;
import com.bookstore.model.User;

public interface UserService {
	
	User createUser(User user, Set<UserRole> userRoles);

}
