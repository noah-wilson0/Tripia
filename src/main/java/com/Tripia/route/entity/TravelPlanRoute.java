package com.Tripia.route.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "travel_plan_route ",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"travel_plan_day_id", "sequence"})
        })
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TravelPlanRoute {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_plan_route_id ")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_plan_day_id",nullable = false)
    private TravelPlanDay travelPlanDay;

    @Column(nullable = false)
    private int sequence;

    @Column(name = "path_type ",nullable = false)
    private int pathType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_place_id",nullable = false)
    private TravelPlanPlace fromPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_place_id",nullable = false)
    private TravelPlanPlace toPlace;

    @Column(nullable = false)
    private int distance;

    @Column(nullable = false)
    private int duration;


}
