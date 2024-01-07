package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class ResAppTest {
    private ResApp ra;

    @BeforeEach
    void runBefore() {
        ra = new ResApp();
    }

    @Test
    void constructorTest() {
        assertEquals(0, ra.getRestaurants().size());
    }

    @Test
    void addRestaurantTest() {
        ra.addRestaurant("A");
        assertEquals(1, ra.getRestaurants().size());
        ra.addRestaurant("B");
        assertEquals(2, ra.getRestaurants().size());
        ra.addRestaurant("A");
        assertEquals(2, ra.getRestaurants().size());
    }
}
