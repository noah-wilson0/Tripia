package com.Tripia.tourapi.location.service;

import com.Tripia.tourapi.location.entity.AreaCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class AreaCodeServiceTest {

    @Autowired
    private AreaCodeService areaCodeService;

    @BeforeEach
    void setUp() {
        AreaCode dummy = new AreaCode(null, "100", "북한");

    }

    @Test
    @DisplayName("지역 코드 저장 테스트")
    void save() {
        String name = "일본";
        String code = "200";
        AreaCode excepted = areaCodeService.save(new AreaCode(null, name, code));
        AreaCode actual = areaCodeService.findByCode(code);
        Assertions.assertEquals(excepted,actual);
    }

    @Test
    @DisplayName("코드로 AreaCode 조회")
    void findByCode() {
        AreaCode result = areaCodeService.findByCode("200");

        assertNotNull(result);
        assertEquals("일본", result.getName());
    }

    @Test
    @DisplayName("이름으로 AreaCode 조회")
    void findByName() {
        AreaCode result = areaCodeService.findByName("중국");

        assertNotNull(result);
        assertEquals("300", result.getCode());
    }

    @Test
    @DisplayName("전체 AreaCode 리스트 조회")
    void findAll() {
        assertFalse(areaCodeService.findAll().isEmpty(),"DB에 지역 코드가 모두 조회되지 않습니다.");
    }
}