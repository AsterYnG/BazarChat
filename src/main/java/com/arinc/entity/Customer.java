package com.arinc.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {
    private Integer customerId;
    private String login;
    private String password;
    private Role roleId;
}
