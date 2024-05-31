package com.arinc.dto;


import lombok.*;

@Value
@Builder
public class UserUpdateProfileDto {
     String nickName;
     String name;
     String surname;
}
