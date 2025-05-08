package com.Tripia.user.entity;

import com.Tripia.route.entity.TravelPlan;
import com.Tripia.utils.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "travel_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role= UserRole.USER;

    @Builder.Default
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt= LocalDate.now();

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "userId")
    private List<TravelPlan> travelPlans=new ArrayList<TravelPlan>();
}
