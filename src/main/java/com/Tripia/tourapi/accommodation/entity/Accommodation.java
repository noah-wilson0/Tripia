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
public class Accommodation {

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
    public void changeCode(String code) {
        this.code = code;
    }

    public void changeAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public void changeAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public void changeImage(String image) {
        this.image = image;
    }

    public void changeLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void changeLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void changeTel(String tel) {
        this.tel = tel;
    }

    public Accommodation changeAll(
            String contentId,
            String title,
            String code,
            String addr1,
            String addr2,
            String image,
            String longitude,
            String latitude,
            String tel
    ) {
        this.contentId = contentId;
        this.title = title;
        this.code = code;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.image = image;
        this.longitude = longitude;
        this.latitude = latitude;
        this.tel = tel;
        return this;
    }

}
