package com.james.training.jwtsecurity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.james.training.jwtsecurity.model.User;
 
 
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User findByEmail(String email);
    User findById(long id);

}