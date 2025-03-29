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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //대체키(기본키)

    @Column(name = "content_id", nullable = false , unique = true)
    private String contentId; //비즈니스 키

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String code;


    @Column(nullable = false)
    private String addr1;

    private String addr2;

    private String image;

    @Column(nullable = false)
    private String longitude; //경도

    @Column(nullable = false)
    private String latitude;  //위도

    private String tel;

    public void changeContentId(String contentId) {
        this.contentId = contentId;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

}
