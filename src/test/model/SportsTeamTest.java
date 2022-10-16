package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;


class SportsTeamTest {
    private SportsTeam team;
    private Contract contract;
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    void runBefore() {
        team = new SportsTeam("Test team");
        contract = new Contract(100000, 5);
        player1 = new Player(contract, "Player1", 25);
        player2 = new Player(contract, "Player2", 26);
        player3 = new Player(contract, "Player3", 27);
    }

    @Test
    void testConstructor() {
        assertEquals(0, team.getPlayers().size());
        assertEquals("Test team", team.getTeamName());
    }

    @Test
    void testAddPlayerNotOnTeam() {
        team.addPlayer(player1);

        assertTrue(team.getPlayers().contains(player1));
    }

    @Test
    void testAddPlayerDuplicatePlayer() {
        team.addPlayer(player1);
        team.addPlayer(player1);

        assertEquals(1, team.getPlayers().size());
        assertEquals(player1, team.getPlayers().get(0));
    }

    @Test
    void testAddPlayerMultiplePlayers() {
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);

        assertEquals(3, team.getPlayers().size());
        assertEquals(player1, team.getPlayers().get(0));
        assertEquals(player2, team.getPlayers().get(1));
        assertEquals(player3, team.getPlayers().get(2));
    }


}