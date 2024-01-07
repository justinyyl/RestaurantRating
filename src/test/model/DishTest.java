package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DishTest {
    private Dish d1;
    private Dish d2;
    private Dish d3;

    @BeforeEach
    void runBefore() {
        d1 = new Dish("D1");
        d2 = new Dish("D2", 9, 8, 1);
        d3 = new Dish("D3");
    }


    @Test
    void constructorTestOne() {
        assertEquals("D1", d1.getName());
        assertEquals(0, d1.getAppearances().size());
        assertEquals(0, d1.getQualities().size());
        assertEquals(0,d1.getTastes().size());
    }

    @Test
    void constructorTestTwo() {
        assertEquals("D2", d2.getName());
        assertEquals(1, d2.getAppearances().size());
        assertEquals(1, d2.getQualities().size());
        assertEquals(1,d2.getTastes().size());
    }

    @Test
    void addScoreTest() {
        d2.addScore(1,3,5);
        assertEquals(2, d2.getAppearances().size());
        assertEquals(2, d2.getQualities().size());
        assertEquals(2,d2.getTastes().size());
        d2.addScore(3,4,7);
        assertEquals(3, d2.getAppearances().size());
        assertEquals(3, d2.getQualities().size());
        assertEquals(3,d2.getTastes().size());
    }

    @Test
    void rateDishTest() {
        d1.addScore(2,3,5);
        d1.addScore(3,5,7);
        d1.addScore(7,8,9);
        assertEquals((12.0 + 16.0 + 21.0) / 9, d1.rateDish());
        assertEquals(-1, d3.rateDish());
    }
}
