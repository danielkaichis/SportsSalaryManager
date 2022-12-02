package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Test for all methods in the SportsTeam class, also includes fields to effectively test these methods
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
        hockeyTeam = new SportsTeam("Test team", "Hockey");
        basketballTeam = new SportsTeam("Test team", "Basketball");
        footballTeam = new SportsTeam("Test team", "Football");
        contract = new Contract(100000, 5);
        maxContract = new Contract(81500000, 5);
        player1 = new Player("Player1", 25, contract);
        player2 = new Player("Player2", 26, contract);
        player3 = new Player("Player3", 27, contract);
        player4 = new Player("Player 4", 30, maxContract);
        EventLog.getInstance().clear();
    }

    @Test
    void testConstructorHockey() {
        assertEquals(0, hockeyTeam.getPlayers().size());
        assertEquals("Test team", hockeyTeam.getTeamName());
        assertEquals("Hockey", hockeyTeam.getSport());
    }

    @Test
    void testConstructorBasketball() {
        assertEquals(0, basketballTeam.getPlayers().size());
        assertEquals("Test team", basketballTeam.getTeamName());
        assertEquals("Basketball", basketballTeam.getSport());
    }

    @Test
    void testConstructorFootball() {
        assertEquals(0, footballTeam.getPlayers().size());
        assertEquals("Test team", footballTeam.getTeamName());
        assertEquals("Football", footballTeam.getSport());
    }

    @Test
    void testAddPlayerNotOnTeam() {
        assertTrue(hockeyTeam.addPlayer(player1));
        assertTrue(hockeyTeam.getPlayers().contains(player1));
    }

    @Test
    void testAddPlayerLog() {
        hockeyTeam.addPlayer(player1);
        List<Event> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();

        for (Event next : el) {
            l.add(next);
        }
        assertEquals("Event log cleared.", l.get(0).getDescription());
        assertEquals("Player Player1 added to Test team.", l.get(1).getDescription());
    }

    @Test
    void testAddPlayerDuplicatePlayer() {
        assertTrue(hockeyTeam.addPlayer(player1));
        assertFalse(hockeyTeam.addPlayer(player1));
        assertEquals(1, hockeyTeam.getPlayers().size());
    }

    @Test
    void testAddPlayerLogDuplicatePlayer() {
        hockeyTeam.addPlayer(player1);
        hockeyTeam.addPlayer(player1);
        List<Event> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();

        for (Event next : el) {
            l.add(next);
        }
        assertEquals("Event log cleared.", l.get(0).getDescription());
        assertEquals("Player Player1 added to Test team.", l.get(1).getDescription());
        assertEquals(2, l.size());
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
    void testAddPlayerLogMultiplePlayers() {
        hockeyTeam.addPlayer(player1);
        hockeyTeam.addPlayer(player2);
        hockeyTeam.addPlayer(player3);

        List<Event> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();

        for (Event next : el) {
            l.add(next);
        }
        assertEquals("Event log cleared.", l.get(0).getDescription());
        assertEquals("Player Player1 added to Test team.", l.get(1).getDescription());
        assertEquals("Player Player2 added to Test team.", l.get(2).getDescription());
        assertEquals("Player Player3 added to Test team.", l.get(3).getDescription());
    }

    @Test
    void testAddPlayerAtSalaryCap() {
        assertTrue(hockeyTeam.addPlayer(player4));
        assertEquals(81500000, hockeyTeam.getTeamSalary());
    }

    @Test
    void testAddPlayerOverSalaryCap() {
        assertTrue(hockeyTeam.addPlayer(player1));
        System.out.println(hockeyTeam.getSalaryCap());
        assertFalse(hockeyTeam.addPlayer(player4));
        assertTrue(hockeyTeam.getTeamSalary() < hockeyTeam.getSalaryCap());
    }

    @Test
    void testRemovePlayer() {
        assertTrue(hockeyTeam.addPlayer(player1));
        assertTrue(hockeyTeam.addPlayer(player2));
        assertTrue(hockeyTeam.addPlayer(player3));
        assertEquals(3, hockeyTeam.getPlayers().size());
        hockeyTeam.removePlayer(player3);
        assertEquals(2, hockeyTeam.getPlayers().size());
        assertFalse(hockeyTeam.getPlayers().contains(player3));
    }

    @Test
    void testRemovePlayerLog() {
        hockeyTeam.addPlayer(player1);
        hockeyTeam.removePlayer(player1);

        List<Event> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();

        for (Event next : el) {
            l.add(next);
        }
        assertEquals("Event log cleared.", l.get(0).getDescription());
        assertEquals("Player Player1 added to Test team.", l.get(1).getDescription());
        assertEquals("Player Player1 removed from Test team.", l.get(2).getDescription());
    }

    @Test
    void testRemovePlayerPlayerNotOnTeam() {
        assertTrue(hockeyTeam.addPlayer(player1));
        assertTrue(hockeyTeam.addPlayer(player2));
        assertTrue(hockeyTeam.addPlayer(player3));
        assertEquals(3, hockeyTeam.getPlayers().size());
        hockeyTeam.removePlayer(player4);
        assertEquals(3, hockeyTeam.getPlayers().size());
    }

    @Test
    void testGetTeamSalary() {
        hockeyTeam.addPlayer(player1);
        assertEquals(100000, hockeyTeam.getTeamSalary());
    }

    @Test
    void testGetTeamSalaryAfterContractExtension() {
        hockeyTeam.addPlayer(player1);
        assertEquals(100000, hockeyTeam.getTeamSalary());
        hockeyTeam.getPlayers().get(0).getContract().extendContract(150000, 2);
        assertEquals(150000, hockeyTeam.getTeamSalary());
    }
}