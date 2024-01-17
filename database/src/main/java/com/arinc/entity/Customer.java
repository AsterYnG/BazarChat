package com.arinc.entity;

import lombok.Builder;
import lombok.Data;

import java.sql.Blob;

@Data
@Builder
public class Customer {
    private Integer customerId;
    private String login;
    private String password;
    private Role roleId;
    private boolean online;
    private String userPic;
}
