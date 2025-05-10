package com.Tripia.route.entity;

import com.Tripia.tourapi.place.entity.Place;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "travel_plan_place",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"travel_plan_day_id", "sequence"})
        })
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class TravelPlanPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_plan_place_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_plan_day_id", nullable = false)
    private TravelPlanDay travelPlanDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(nullable = false)
    private int sequence;

    @Column(name = "start_time", nullable = false)
    private LocalTime  startTime;
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

}
