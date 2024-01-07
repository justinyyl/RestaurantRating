package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            ResApp ra = new ResApp();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            ResApp ra = new ResApp();
            JsonWriter writer = new JsonWriter("./data/testReaderEmptyResApp.json");
            writer.open();
            writer.write(ra);
            writer.close();

            JsonReader reader = new JsonReader("./data/testReaderEmptyResApp.json");
            ra = reader.read();
            assertEquals(0, ra.getRestaurants().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            ResApp ra = new ResApp();
            ra.addRestaurant("apple");
            Restaurant r1 = ra.getRestaurants().get(0);
            r1.addEnvironmentScore(4,5,6);
            r1.addGradedDish("juice", 3, 4, 5);
            JsonWriter writer = new JsonWriter("./data/testReaderGeneralResApp.json");
            writer.open();
            writer.write(ra);
            writer.close();

            JsonReader reader = new JsonReader("./data/testReaderGeneralResApp.json");
            ra = reader.read();
            assertEquals(1, ra.getRestaurants().size());
            r1 = ra.getRestaurants().get(0);
            assertEquals("apple", r1.getName());
            assertEquals(1, r1.getEnvironmentScores().size());
            Dish d1 = r1.getDishes().get(0);
            assertEquals("juice", d1.getName());
            assertEquals((3.0+4.0+5.0)/3, d1.rateDish());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
