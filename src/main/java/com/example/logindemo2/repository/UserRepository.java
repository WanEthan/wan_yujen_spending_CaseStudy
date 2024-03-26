///////// DO NOT MODIFY THE CODE!!!!!!

package com.example.logindemo2.repository;

import com.example.logindemo2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findUserByEmail(String email);
    public User findUserByFullName(String fullName);
}