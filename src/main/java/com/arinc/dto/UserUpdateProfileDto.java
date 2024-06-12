package com.arinc.dto;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Value
@Builder
public class UserUpdateProfileDto {
     String nickName;
     String name;
     String surname;
     MultipartFile userPic;
}
