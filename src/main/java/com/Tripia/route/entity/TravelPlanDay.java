package com.Tripia.route.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "travel_plan_day",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"travel_plan_id", "sequence"})
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class TravelPlanDay {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_plan_day_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_plan_id",nullable = false)
    private TravelPlan travelPlan;

    @Column(nullable = false)
    private int sequence;

    @Column(nullable = false)
    private LocalDate date;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "travelPlanDay", fetch = FetchType.LAZY)
    private List<TravelPlanRoute> travelPlanPlaces = new ArrayList<>();



}
