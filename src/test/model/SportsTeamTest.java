package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;


class SportsTeamTest {
    private SportsTeam hockeyTeam;
    private SportsTeam basketballTeam;
    private SportsTeam footballTeam;
    private Contract contract;
    private Contract maxContract;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    @BeforeEach
    void runBefore() {
        hockeyTeam = new SportsTeam("Test team", "hockey");
        basketballTeam = new SportsTeam("Test team", "basketball");
        footballTeam = new SportsTeam("Test team", "football");
        contract = new Contract(100000, 5);
        maxContract = new Contract(81500000, 5);
        player1 = new Player(contract, "Player1", 25);
        player2 = new Player(contract, "Player2", 26);
        player3 = new Player(contract, "Player3", 27);
        player4 = new Player(maxContract, "Player 4", 30);
    }

    @Test
    void testConstructorHockey() {
        assertEquals(0, hockeyTeam.getPlayers().size());
        assertEquals("Test team", hockeyTeam.getTeamName());
        assertEquals("hockey", hockeyTeam.getSport());
    }

    @Test
    void testConstructorBasketball() {
        assertEquals(0, basketballTeam.getPlayers().size());
        assertEquals("Test team", basketballTeam.getTeamName());
        assertEquals("basketball", basketballTeam.getSport());
    }

    @Test
    void testConstructorFootball() {
        assertEquals(0, footballTeam.getPlayers().size());
        assertEquals("Test team", footballTeam.getTeamName());
        assertEquals("football", footballTeam.getSport());
    }

    @Test
    void testAddPlayerNotOnTeam() {
        assertTrue(hockeyTeam.addPlayer(player1));
        assertTrue(hockeyTeam.getPlayers().contains(player1));
    }

    @Test
    void testAddPlayerDuplicatePlayer() {
        assertTrue(hockeyTeam.addPlayer(player1));
        assertFalse(hockeyTeam.addPlayer(player1));
        assertEquals(1, hockeyTeam.getPlayers().size());
    }

    @Test
    void testAddPlayerMultiplePlayers() {
        assertTrue(hockeyTeam.addPlayer(player1));
        assertTrue(hockeyTeam.addPlayer(player2));
        assertTrue(hockeyTeam.addPlayer(player3));

        assertEquals(3, hockeyTeam.getPlayers().size());
        assertEquals(player1, hockeyTeam.getPlayers().get(0));
        assertEquals(player2, hockeyTeam.getPlayers().get(1));
        assertEquals(player3, hockeyTeam.getPlayers().get(2));
    }

    @Test
    void testAddPlayerAtSalaryCap() {
        assertTrue(hockeyTeam.addPlayer(player4));
        assertEquals(81500000, hockeyTeam.getTeamSalary());
    }

    @Test
    void testAddPlayerOverSalaryCap() {
        assertTrue(hockeyTeam.addPlayer(player1));
        assertFalse(hockeyTeam.addPlayer(player4));
    }

}