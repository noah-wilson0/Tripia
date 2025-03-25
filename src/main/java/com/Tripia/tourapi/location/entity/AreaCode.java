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
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String code;

    public void changeCode(String newCode) {
        this.code = newCode;
    }
}

