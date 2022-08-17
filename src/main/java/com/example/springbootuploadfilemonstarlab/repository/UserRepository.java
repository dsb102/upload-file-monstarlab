package com.example.springbootuploadfilemonstarlab.repository;

import com.example.springbootuploadfilemonstarlab.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);
}
