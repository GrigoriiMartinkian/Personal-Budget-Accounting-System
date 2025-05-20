package com.example.financeproject.repositories.user;
import com.example.financeproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

}

