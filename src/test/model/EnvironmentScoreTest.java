package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnvironmentScoreTest {
    private EnvironmentScore e1;


    @BeforeEach
    void runBefore() {
        e1 = new EnvironmentScore(3, 9, 4);
    }

    @Test
    void ConstructorTest() {
        assertEquals(3, e1.getAmbience());
        assertEquals(9, e1.getServiceQuality());
        assertEquals(4, e1.getCleanliness());
    }


}
