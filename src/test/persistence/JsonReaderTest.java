package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ResApp ra = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyResApp() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyResApp.json");
        try {
            ResApp ra = reader.read();
            assertEquals(0, ra.getRestaurants().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralResApp() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralResApp.json");
        try {
            ResApp ra = reader.read();
            assertEquals(1, ra.getRestaurants().size());
            Restaurant r1 = ra.getRestaurants().get(0);
            assertEquals("apple", r1.getName());
            assertEquals(1, r1.getEnvironmentScores().size());
            Dish d1 = r1.getDishes().get(0);
            assertEquals("juice", d1.getName());
            assertEquals((3.0+4.0+5.0)/3, d1.rateDish());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
