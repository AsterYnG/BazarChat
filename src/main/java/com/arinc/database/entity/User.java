package com.arinc.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JoinFormula;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "\"user\"")
public class User implements BaseEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String login;
    private String password;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
    private boolean online;
    private String userPic;
    private String email;
    private String name;
    private String surname;
    private String nickname;
}
