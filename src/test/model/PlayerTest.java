package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Contract testContract;
    private Player testPlayer;

    @BeforeEach
    void runBefore() {
        testContract = new Contract(100000, 5);
        testPlayer = new Player(testContract, "Wayne Gretzky", 25);
    }

    @Test
    void testConstructor() {
        assertEquals(testContract, testPlayer.getContract());
        assertEquals("Wayne Gretzky", testPlayer.getName());
        assertEquals(25, testPlayer.getAge());
    }
}
