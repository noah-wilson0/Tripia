package com.Tripia.route.entity;

import com.Tripia.tourapi.place.entity.Place;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "travel_plan_place")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TravelPlanPlace {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_plan_place_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_plan_day_id",nullable = false)
    private TravelPlanDay travelPlanDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(nullable = false)
    private int sequence;



}
