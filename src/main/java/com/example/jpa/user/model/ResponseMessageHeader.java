package com.example.jpa.user.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseMessageHeader {


    private  boolean result;
    private  String resultCode;
    private  String message;
    private  int status;

}
