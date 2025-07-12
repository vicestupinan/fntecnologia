package com.vmestupinan.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vmestupinan.auth.model.User;

public interface UserRepository extends JpaRepository <User, Long>{
    
}
