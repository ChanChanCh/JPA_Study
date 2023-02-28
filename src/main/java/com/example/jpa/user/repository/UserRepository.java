package com.example.jpa.user.repository;

import com.example.jpa.user.entity.User;
import com.example.jpa.user.model.UserInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    int countByEmail(String email);

    Optional<User> findByEmail(String email);

    List<User> findByEmailContainsOrPhoneContainsOrUserNameContains(String email, String phone, String UserName);

}
