package com.fileuploaddownload.filemgtdemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fileuploaddownload.filemgtdemo.model.Role;
import com.fileuploaddownload.filemgtdemo.model.RoleName;
 
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}