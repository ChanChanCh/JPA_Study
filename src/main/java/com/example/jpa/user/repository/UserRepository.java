package com.example.jpa.user.repository;

import com.example.jpa.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.time.LocalDateTime;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {




}
