package persistence;

import model.Contract;
import model.Player;
import model.SportsTeam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Test for all methods in the JsonWriter class, also includes fields to effectively test these methods
public class JsonWriterTest extends JsonTest {
    private Player player1;
    private Player player2;
    private Contract contract1;
    private Contract contract2;

    @BeforeEach
    void setup() {
        contract1 = new Contract(2000000, 5);
        player1 = new Player("John", 23, contract1);
        contract2 = new Contract(5000000, 8);
        player2 = new Player("Steve", 26, contract2);
    }

    @Test
    void testWriterInvalidFile() {
        // based on the supplied workroom example for CPSC 210
        // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        try {
            SportsTeam team = new SportsTeam("Test team", "hockey");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptySportsTeam() {
        try {
            SportsTeam team = new SportsTeam("Test team", "hockey");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySportsTeam.json");
            writer.open();
            writer.write(team);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterEmptySportsTeam.json");
            team = reader.read();
            assertEquals("Test team", team.getTeamName());
            assertEquals("hockey", team.getSport());
            assertEquals(0, team.getPlayers().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralSportsTeam() {
        try {
            SportsTeam team = new SportsTeam("Test team", "hockey");
            team.addPlayer(player1);
            team.addPlayer(player2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralSportsTeam.json");
            writer.open();
            writer.write(team);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralSportsTeam.json");
            team = reader.read();
            assertEquals("Test team", team.getTeamName());
            assertEquals("hockey", team.getSport());
            List<Player> players = team.getPlayers();
            assertEquals(2, players.size());
            checkPlayer("John", 23, contract1, players.get(0));
            checkPlayer("Steve", 26, contract2, players.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
