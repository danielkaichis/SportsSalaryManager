package persistence;

import model.Contract;
import model.Player;
import model.SportsTeam;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {
    private Contract contract1;
    private Contract contract2;

    @BeforeEach
    void setup() {
        contract1 = new Contract(2000000, 5);
        contract2 = new Contract(5000000, 8);
    }

    // based on the supplied workroom example for CPSC 210
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/badfile.json");
        try {
            SportsTeam team = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        } catch (JSONException e) {
            fail("JSONException was thrown");
        }
    }

    @Test
    void testReaderInvalidFile() {
        JsonReader reader = new JsonReader("./data/testReaderInvalidFile.json");
        try {
            SportsTeam team = reader.read();
            fail("JSONException expected");
        } catch (IOException e) {
            fail("IOException was thrown");
        } catch (JSONException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptySportsTeam() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySportsTeam.json");
        try {
            SportsTeam team = reader.read();
            assertEquals("Empty team", team.getTeamName());
            assertEquals("hockey", team.getSport());
            assertEquals(0, team.getPlayers().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralSportsTeam() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSportsTeam.json");
        try {
            SportsTeam team = reader.read();
            assertEquals("Test team", team.getTeamName());
            assertEquals("hockey", team.getSport());
            List<Player> players = team.getPlayers();
            assertEquals(2, team.getPlayers().size());
            checkPlayer("John", 23, contract1, players.get(0));
            checkPlayer("Steve", 26, contract2, players.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
