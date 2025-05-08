package com.Tripia.route.entity;

import com.Tripia.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "travel_plan")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class TravelPlan {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_plan_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User userId;

    @Column(nullable = false)
    private String title;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Builder.Default
    @Column(name = "created_at", nullable = false)
    private LocalDate createAt=LocalDate.now();

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "travelPlan",fetch = FetchType.LAZY)
    private List<TravelPlanDay> travelPlanDays= new ArrayList<>();


}
