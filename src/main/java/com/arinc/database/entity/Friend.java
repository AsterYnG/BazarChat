package com.arinc.database.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Friend implements BaseEntity<Integer>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User ownerId;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friendId;

    @JoinColumn(name = "status_id")
    @ManyToOne
    private FriendStatus statusId;
    private LocalDateTime submittedDate;
}
