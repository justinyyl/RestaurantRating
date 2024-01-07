package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class RestaurantTest {
    private Restaurant r1;
    private Restaurant r2;
    private Restaurant r3;


    @BeforeEach
    void runBefore() {
        r1 = new Restaurant("R1");
        r2 = new Restaurant("R2");
        r3 = new Restaurant("R3");
    }


    @Test
    void testConstructor() {
        assertEquals("R1", r1.getName());
        assertEquals(0, r1.getDishes().size());
        assertEquals(0, r1.getEnvironmentScores().size());
    }


    @Test
    void addDishesTest() {
        r1.addDish("A");
        assertEquals(1, r1.getDishes().size());
        r1.addDish("B");
        assertEquals(2, r1.getDishes().size());
        r1.addDish("A");
        assertEquals(2, r1.getDishes().size());
    }

    @Test
    void addGradedDishTest() {
        r1.addDish("A");
        assertEquals(1, r1.getDishes().size());
        assertEquals(0, r1.getDishes().get(0).getTastes().size());
        assertEquals(0, r1.getDishes().get(0).getAppearances().size());
        assertEquals(0, r1.getDishes().get(0).getQualities().size());
        r1.addGradedDish("A", 2, 4, 4);
        assertEquals(1, r1.getDishes().size());
        assertEquals(1, r1.getDishes().get(0).getTastes().size());
        assertEquals(1, r1.getDishes().get(0).getAppearances().size());
        assertEquals(1, r1.getDishes().get(0).getQualities().size());
        r1.addGradedDish("B", 3, 5, 7);
        assertEquals(2, r1.getDishes().size());
        assertEquals(1, r1.getDishes().get(1).getTastes().size());
        assertEquals(1, r1.getDishes().get(1).getAppearances().size());
        assertEquals(1, r1.getDishes().get(1).getQualities().size());
    }


    @Test
    void addEnvironmentScoreTest() {
        r1.addEnvironmentScore(2, 9, 8);
        assertEquals(1, r1.getEnvironmentScores().size());
        r1.addEnvironmentScore(3, 7, 4);
        assertEquals(2, r1.getEnvironmentScores().size());

    }

    @Test
    void rateEnvironmentScoresTest() {
        r1.addEnvironmentScore(2, 9, 8);
        r1.addEnvironmentScore(7, 1, 6);
        r1.addEnvironmentScore(3, 7, 4);
        assertEquals((12.0 + 17.0 + 18.0) / 9, r1.rateEnvironmentScores());
        assertEquals(-1, r3.rateEnvironmentScores());

    }

    @Test
    void rateDishScoresTest() {
        r1.addGradedDish("A", 3,5,6);
        r1.addGradedDish("A", 6,7,8);
        r1.addGradedDish("B", 5,6,3);
        r1.addDish("C");
        assertEquals(((9.0 + 12.0 + 14.0) / 6 + (5.0 + 6.0 + 3.0) / 3) / 2, r1.rateDishScores());
        assertEquals(-1, r2.rateDishScores());

    }

    @Test
    void rateRestaurantTest() {
        r1.addEnvironmentScore(2, 9, 8);
        r1.addEnvironmentScore(7, 1, 6);
        r1.addEnvironmentScore(3, 7, 4);
        r1.addGradedDish("A", 3,5,6);
        r1.addGradedDish("A", 6,7,8);
        r1.addGradedDish("B", 5,6,3);
        r1.addDish("C");
        assertEquals((r1.rateDishScores() + r1.rateEnvironmentScores()) / 2, r1.rateRestaurant());
        r2.addEnvironmentScore(3,4,5);
        assertEquals(-1, r2.rateRestaurant());
        r3.addGradedDish("A",4,5,1);
        assertEquals(-1, r3.rateRestaurant());

    }
}
