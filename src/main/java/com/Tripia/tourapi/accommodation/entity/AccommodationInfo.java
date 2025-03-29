package com.Tripia.tourapi.accommodation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "accommodation_info")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccommodationInfo {

    @Id
    @Column(name = "content_id")
    private String contentId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String code;


    @Column(nullable = false)
    private String addr1;

    private String addr2;

    private String image;

    @Column(nullable = false,unique = true)
    private String longitude; //경도

    @Column(nullable = false,unique = true)
    private String latitude;  //위도

    private String tel;

    public void changeContentId(String contentId) {
        this.contentId = contentId;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

}
