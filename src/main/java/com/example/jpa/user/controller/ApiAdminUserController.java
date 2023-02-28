package com.example.jpa.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jpa.notice.entity.Notice;
import com.example.jpa.notice.model.NoticeResponse;
import com.example.jpa.notice.model.ResponseError;
import com.example.jpa.notice.repository.NoticeRepository;
import com.example.jpa.user.entity.User;
import com.example.jpa.user.exception.ExsitsEmailException;
import com.example.jpa.user.exception.PasswordNotMatchException;
import com.example.jpa.user.exception.UserNotFoundException;
import com.example.jpa.user.model.*;
import com.example.jpa.user.repository.UserRepository;
import com.example.jpa.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
public class ApiAdminUserController {

    private final UserRepository userRepository;


    /*
         48. 사용자 목록과 사용자 수를 함께 내리는 REST API를 작성해 보세요
         ResponseData의 구조를 아래와 같이 형식으로 작성해서 결과 리턴
         {
            "totalCount" : N
            , "data": user목록 컬렉션
         }
     */
//    @GetMapping("/api/admin/user")
//    public ResponseMessage userList(){
//
//        List<User> userList = userRepository.findAll();
//        long totalUserCount = userRepository.count();
//
//        return ResponseMessage.builder()
//                .data(userList)
//                .totalCount(totalUserCount)
//                .build();
//    }


    /*
        49. 사용자 상세 조회를 조회하는 API를 아래 조건에 맞게 구현해 보세요.
        - ResponseMessage 클래스로 추상화해서 전송
        {
        "header": {
        result: true | false
        , resultCode: string
        , message: errror message or alert message
        , status: http result code
        },
        "body": 내려야 할 데이터가 있는 경우  body를 통해서 전송
        }
     */
    @GetMapping("/api/admin/user/{id}")
    public ResponseEntity<?> userDetail(@PathVariable Long id){

        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){

            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().body(ResponseMessage.success(user));
    }





}
