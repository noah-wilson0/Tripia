package com.Tripia.tourapi.location.service;

import com.Tripia.tourapi.location.entity.AreaCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class AreaCodeServiceTest {

    @Autowired
    private AreaCodeService areaCodeService;

    private Optional<AreaCode> savedAreaCode;

    @BeforeEach
    void setUp() {
        savedAreaCode=areaCodeService.findByName("서울");

    }

    @Test
    @DisplayName("지역 코드 저장 테스트")
    void save() {
        String name = "일본";
        String code = "200";
        AreaCode excepted = areaCodeService.save(new AreaCode(null, name, code));
        Optional<AreaCode> actual = areaCodeService.findByCode(code);
        Assertions.assertEquals(excepted,actual.get());
    }

    @Test
    @DisplayName("코드로 AreaCode 조회")
    void findByCode() {
        Optional<AreaCode> result = areaCodeService.findByCode(savedAreaCode.get().getCode());

        assertNotNull(result);
        assertEquals("서울", result.get().getName());
    }

    @Test
    @DisplayName("이름으로 AreaCode 조회")
    void findByName() {
        Optional<AreaCode> result = areaCodeService.findByName("서울");
        assertNotNull(result);
        assertEquals(savedAreaCode.get().getCode(), result.get().getCode());
    }

    @Test
    @DisplayName("전체 AreaCode 리스트 조회")
    void findAll() {
        assertFalse(areaCodeService.findAll().isEmpty(),"DB에 지역 코드가 모두 조회되지 않습니다.");
    }
}