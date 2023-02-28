package com.example.jpa.user.model;


import com.example.jpa.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {

    private long id;
    
    private String email;

    private String userName;

    private String phone;


    public static UserResponse of(User user)  {
        return UserResponse.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();

    }
}
