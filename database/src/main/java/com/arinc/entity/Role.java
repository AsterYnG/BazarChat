package com.arinc.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Role {
    private Integer roleId;
    private String roleName;

}
