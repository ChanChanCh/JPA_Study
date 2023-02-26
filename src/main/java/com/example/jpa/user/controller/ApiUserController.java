package com.example.jpa.user.controller;

import com.example.jpa.notice.entity.Notice;
import com.example.jpa.notice.model.ResponseError;
import com.example.jpa.notice.repository.NoticeRepository;
import com.example.jpa.user.entity.User;
import com.example.jpa.user.exception.UserNotFoundException;
import com.example.jpa.notice.model.NoticeResponse;
import com.example.jpa.user.model.UserInput;
import com.example.jpa.user.model.UserResponse;
import com.example.jpa.user.model.UserUpdate;
import com.example.jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
Q31번문제
        사용자 등록시 입력값이 유효하지 않은 경우 예외를 발생시키는 기능을 작성해 보세요.
        - 입력값: 이메일(ID), 이름, 비밀번호, 연락처
        - 사용자 정의 에러 모델을 이용하여 에러를 리턴

 */

@RequiredArgsConstructor
@RestController
public class ApiUserController {

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;

//    @PostMapping("/api/user")
//    public ResponseEntity<?> addUser(@RequestBody @Valid UserInput userInput, Errors errors){
//
//        List<ResponseError> responseErrorList = new ArrayList<>();
//
//        if(errors.hasErrors()){
//            errors.getAllErrors().forEach(e->{
//                responseErrorList.add(ResponseError.of((FieldError)e));
//            });
//
//            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
//        }
//
////        return ResponseEntity.ok().build();
//         return new ResponseEntity<>(HttpStatus.OK);
//    }



    @PostMapping("/api/user")
    public ResponseEntity<?> addUser(@RequestBody @Valid UserInput userInput, Errors errors){

        List<ResponseError> responseErrorList = new ArrayList<>();

        if(errors.hasErrors()){
            errors.getAllErrors().forEach(e->{
                responseErrorList.add(ResponseError.of((FieldError)e));
            });

            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .email(userInput.getEmail())
                .userName((userInput.getUserName()))
                .password(userInput.getPassword())
                .phone(userInput.getPhone())
                .regDate(LocalDateTime.now())
                .build();
        userRepository.save(user);


        return ResponseEntity.ok().build();

    }

    @PutMapping("/api/user/{id}")
    public ResponseEntity<?> updateuser(@PathVariable Long id, @RequestBody @Valid UserUpdate userUpdate, Errors errors){

        List<ResponseError> responseErrorList = new ArrayList<>();
        if(errors.hasErrors()){
            errors.getAllErrors().forEach(e->{
                responseErrorList.add(ResponseError.of((FieldError)e));
            });
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        user.setPhone(userUpdate.getPhone());
        user.setUpdateDate(LocalDateTime.now());
        userRepository.save(user);

        return ResponseEntity.ok().build();

    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundExceptionHandler(UserNotFoundException exception){

        return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);

    }


    /*
    ####34. 사용자 정보 조회(가입한 아이디에 대한)의 기능을 수행하는 API를 작성해 보세요.

        다만, 보안상 비밀번호와 가입일, 회원정보 수정일은 내리지 않는다.
     */

    @GetMapping("/api/user/{id}")
    public UserResponse getUser(@PathVariable Long id){

        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("사용자의 정보가 없습니다"));

        UserResponse userResponse = UserResponse.of(user);

        return userResponse;
    }

    @GetMapping("/api/user/{id}/notice")
    public List<NoticeResponse> userNotice(@PathVariable Long id){

        User user = userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("사용자의 정보가 없습니다."));

        List<Notice> noticeList = noticeRepository.findByUser(user);

        List<NoticeResponse> noticeResponseList = new ArrayList<>();

        noticeList.stream().forEach((e)->{
            noticeResponseList.add(NoticeResponse.of(e));
        });

        return noticeResponseList;

    }







}
