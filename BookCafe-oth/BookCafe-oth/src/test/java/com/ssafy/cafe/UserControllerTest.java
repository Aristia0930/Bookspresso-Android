package com.ssafy.cafe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.ssafy.cafe.controller.rest.UserRestController;

/**
 * @author tasehik.heo
 * @since 2021. 6. 28.
 */
@SpringBootTest
public class UserControllerTest {

    @Autowired
    UserRestController uc;
    
    @Test
    public void testBean() {
        int stamp = 1;
        Map<String, Object> info = uc.getGrade(stamp);
        assertEquals("씨앗", info.get("title"));
        assertEquals(1, info.get("step"));
        assertEquals(10, info.get("stepMax"));
        assertEquals(10, info.get("to"));
        System.out.println(1);

        stamp = 2;
        info = uc.getGrade(stamp);
        assertEquals("씨앗", info.get("title"));
        assertEquals(1, info.get("step"));
        assertEquals(10, info.get("stepMax"));
        assertEquals(9, info.get("to"));
        System.out.println(2);
        
        stamp = 10;
        info = uc.getGrade(stamp);
        assertEquals("씨앗", info.get("title"));
        assertEquals(1, info.get("step"));
        assertEquals(10, info.get("stepMax"));
        assertEquals(1, info.get("to"));
        System.out.println(3);
        
        stamp = 11;
        info = uc.getGrade(stamp);
        assertEquals("씨앗", info.get("title"));
        assertEquals(2, info.get("step"));
        assertEquals(10, info.get("stepMax"));
        assertEquals(10, info.get("to"));
        System.out.println(4);
        

        stamp = 20;
        info = uc.getGrade(stamp);
        assertEquals("씨앗", info.get("title"));
        assertEquals(2, info.get("step"));
        assertEquals(10, info.get("stepMax"));
        assertEquals(1, info.get("to"));
        System.out.println(5);

        stamp = 21;
        info = uc.getGrade(stamp);
        assertEquals("씨앗", info.get("title"));
        assertEquals(3, info.get("step"));
        assertEquals(10, info.get("stepMax"));
        assertEquals(10, info.get("to"));
        System.out.println(6);

        stamp = 50;
        info = uc.getGrade(stamp);
        assertEquals("씨앗", info.get("title"));
        assertEquals(5, info.get("step"));
        assertEquals(10, info.get("stepMax"));
        assertEquals(1, info.get("to"));
        System.out.println(7);

        stamp = 51;
        info = uc.getGrade(stamp);
        assertEquals("꽃", info.get("title"));
        assertEquals(1, info.get("step"));
        assertEquals(15, info.get("stepMax"));
        assertEquals(15, info.get("to"));
        System.out.println(8);

        stamp = 65;
        info = uc.getGrade(stamp);
        assertEquals("꽃", info.get("title"));
        assertEquals(1, info.get("step"));
        assertEquals(15, info.get("stepMax"));
        assertEquals(1, info.get("to"));
        System.out.println(9);

        stamp = 66;
        info = uc.getGrade(stamp);
        assertEquals("꽃", info.get("title"));
        assertEquals(2, info.get("step"));
        assertEquals(15, info.get("stepMax"));
        assertEquals(15, info.get("to"));
        System.out.println(10);

        stamp = 225;
        info = uc.getGrade(stamp);
        assertEquals("열매", info.get("title"));
        assertEquals(5, info.get("step"));
        assertEquals(20, info.get("stepMax"));
        assertEquals(1, info.get("to"));
        System.out.println(11);
        

        stamp = 226;
        info = uc.getGrade(stamp);
        assertEquals("커피콩", info.get("title"));
        assertEquals(1, info.get("step"));
        assertEquals(25, info.get("stepMax"));
        assertEquals(25, info.get("to"));
        System.out.println(12);
        

        stamp = 250;
        info = uc.getGrade(stamp);
        assertEquals("커피콩", info.get("title"));
        assertEquals(1, info.get("step"));
        assertEquals(25, info.get("stepMax"));
        assertEquals(1, info.get("to"));
        System.out.println(13);

        stamp = 251;
        info = uc.getGrade(stamp);
        assertEquals("커피콩", info.get("title"));
        assertEquals(2, info.get("step"));
        assertEquals(25, info.get("stepMax"));
        assertEquals(25, info.get("to"));
        System.out.println(14);


        stamp = 0;
        info = uc.getGrade(stamp);
        assertEquals("씨앗", info.get("title"));
        assertEquals(0, info.get("step"));
        assertEquals(10, info.get("stepMax"));
        assertEquals(1, info.get("to"));

    }
}
