package com.Tripia.tourapi.accommodation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "accommodation_info")
public class AccommodationInfo {

    @Id
    private Long id;

    private String addr1;
    private String addr2;

}
