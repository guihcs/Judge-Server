package test;

import database.Database;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseTest {


    @Test
    public void testDatabase(){
        String data = "metacarpofalangeana82130912793aoscoascjoisac";

        Database.save("test", data);

        String result = Database.load("test");

        assertEquals(data, result);
    }
}
