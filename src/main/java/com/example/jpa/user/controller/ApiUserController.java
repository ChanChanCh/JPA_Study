package com.example.jpa.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jpa.notice.entity.Notice;
import com.example.jpa.notice.model.ResponseError;
import com.example.jpa.notice.repository.NoticeRepository;
import com.example.jpa.user.entity.User;
import com.example.jpa.user.exception.ExsitsEmailException;
import com.example.jpa.user.exception.PasswordNotMatchException;
import com.example.jpa.user.exception.UserNotFoundException;
import com.example.jpa.notice.model.NoticeResponse;
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


//
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
//        User user = User.builder()
//                .email(userInput.getEmail())
//                .userName((userInput.getUserName()))
//                .password(userInput.getPassword())
//                .phone(userInput.getPhone())
//                .regDate(LocalDateTime.now())
//                .build();
//        userRepository.save(user);
//
//
//        return ResponseEntity.ok().build();
//
//    }

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

    /*
            사용자 등록시 이미 존재하는 이메일(이메일은 유일)인 경우 예외를 발생시키는 API 작성
            동일한 이메일에 가입된 회원정보가 존재하는 경우 ExsitsEmailException 발생
     */
//
//    @PostMapping("/api/user")
//    public ResponseEntity<?> addUser(@RequestBody @Valid UserInput userInput, Errors errors){
//
//        List<ResponseError> responseErrorList = new ArrayList<>();
//        if(errors.hasErrors()){
//            errors.getAllErrors().stream().forEach((e)->{
//                responseErrorList.add(ResponseError.of((FieldError)e));
//            });
//            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
//        }
//
//        if(userRepository.countByEmail(userInput.getEmail()) > 0){
//            throw new ExsitsEmailException("이미 존재하는 이메일 입니다.");
//        }
//
//        User user = User.builder()
//                .email(userInput.getEmail())
//                .userName(userInput.getUserName())
//                .phone(userInput.getPhone())
//                .password(userInput.getPassword())
//                .regDate(LocalDateTime.now())
//                .build();
//        userRepository.save(user);
//
//        return ResponseEntity.ok().build();
//
//    }

/*
       Q38. 회원가입시 비밀번호를 암호화하여 저장하는 API를 작성
 */

    private String getEncryptPassword(String password){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    @PostMapping("/api/user")
    public ResponseEntity<?> addUser(@RequestBody @Valid UserInput userInput, Errors errors){

        List<ResponseError> responseErrorList = new ArrayList<>();
        if(errors.hasErrors()){
            errors.getAllErrors().stream().forEach((e)->{
                responseErrorList.add(ResponseError.of((FieldError)e));
            });
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        if(userRepository.countByEmail(userInput.getEmail()) > 0){
            throw new ExsitsEmailException("이미 존재하는 이메일 입니다.");
        }

        String encryptPassword = getEncryptPassword(userInput.getPassword());


        User user = User.builder()
                .email(userInput.getEmail())
                .userName(userInput.getUserName())
                .phone(userInput.getPhone())
                .password(encryptPassword)
                .regDate(LocalDateTime.now())
                .build();
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }








    @ExceptionHandler(ExsitsEmailException.class)
    public ResponseEntity<?> ExsitsEmailExceptionHandler(ExsitsEmailException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }


    /**
     * 43. 사용자 이메일과 비밀번호를 통해서 JWT를 발행하는 API를 작성
         [조건]
         - JWT 토큰발행시 사용자 정보가 유효하지 않을때 예외 발생
         - 사용자정보가 존재하지 않는경우(UserNotFoundException)에 대해서 예외 발생
         - 비밀번호가 일치하지 않는 경우 (PasswordNotMatchException)에 대해서 예외 발생
     */


    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<?> PasswordNotMatchExceptionHandler(PasswordNotMatchException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

//    @PostMapping("/api/user/login")
//    public ResponseEntity<?> createToken(@RequestBody @Valid UserLogin userLogin, Errors errors){
//
//        List<ResponseError> responseErrorList = new ArrayList<>();
//        if(errors.hasErrors()){
//            errors.getAllErrors().stream().forEach((e)->{
//                responseErrorList.add(ResponseError.of((FieldError)e));
//            });
//            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
//        }
//
//        User user = userRepository.findByEmail(userLogin.getEmail())
//                .orElseThrow(()->new UserNotFoundException("사용자의 정보가 없습니다."));
//
//        if(!PasswordUtils.equalPassword(userLogin.getPassword(), user.getPassword())){
//            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
//        }
//
//        return ResponseEntity.ok().build();
//    }
//
//

    /*
        44. 사용자의 이메일과 비밀번호를 통해서 JWT를 발행하는 로직
        - JWT 토큰 발행
     */


//    @PostMapping("/api/user/login")
//    public ResponseEntity<?> createToken(@RequestBody @Valid UserLogin userLogin, Errors errors){
//
//        List<ResponseError> responseErrorList = new ArrayList<>();
//        if(errors.hasErrors()){
//            errors.getAllErrors().stream().forEach((e)->{
//                responseErrorList.add(ResponseError.of((FieldError)e));
//            });
//            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
//        }
//
//        User user = userRepository.findByEmail(userLogin.getEmail())
//                .orElseThrow(()->new UserNotFoundException("사용자의 정보가 없습니다."));
//
//        if(!PasswordUtils.equalPassword(userLogin.getPassword(), user.getPassword())){
//            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
//        }
//
//        //토큰발행시점
//
//        String token = JWT.create()
//                .withExpiresAt(new Date())
//                .withClaim("user_id", user.getId())
//                .withIssuer(user.getUserName())
//                .withIssuer(user.getEmail())
//                .sign(Algorithm.HMAC512("fastcampus".getBytes()));
//
//
//        return ResponseEntity.ok().body(UserLoginToken.builder().token(token).build());
//    }


    /*
        45. JWT  토큰 발행시 발생 유효기간을 1개월로 저장하는 API 작성
     */

    @PostMapping("/api/user/login")
    public ResponseEntity<?> createToken(@RequestBody @Valid UserLogin userLogin, Errors errors){

        List<ResponseError> responseErrorList = new ArrayList<>();
        if(errors.hasErrors()){
            errors.getAllErrors().stream().forEach((e)->{
                responseErrorList.add(ResponseError.of((FieldError)e));
            });
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByEmail(userLogin.getEmail())
                .orElseThrow(()->new UserNotFoundException("사용자의 정보가 없습니다."));

        if(!PasswordUtils.equalPassword(userLogin.getPassword(), user.getPassword())){
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }

        LocalDateTime expiredDateTime = LocalDateTime.now().plusMonths(1);
        Date expireDate = java.sql.Timestamp.valueOf(expiredDateTime);

        //토큰발행시점

        String token = JWT.create()
                .withExpiresAt(expireDate)
                .withClaim("user_id", user.getId())
                .withIssuer(user.getUserName())
                .withIssuer(user.getEmail())
                .sign(Algorithm.HMAC512("fastcampus".getBytes()));


        return ResponseEntity.ok().body(UserLoginToken.builder().token(token).build());
    }




}
