package com.Tripia.tourapi.location.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "area_code")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AreaCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //대체키(기본키)

    @Column(nullable = false, unique = true)
    private String name; //비즈니스 키

    @Column(nullable = false)
    private String code;

    public void changeCode(String newCode) {
        this.code = newCode;
    }

    public AreaCode changeAll(String name, String code) {
        this.name = name;
        this.code = code;

        return this;
    }

}

