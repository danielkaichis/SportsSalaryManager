package model;

import java.util.ArrayList;
import java.util.List;

public class SportsTeam {
    private List<Player> players;
    private String teamName;

    // MODIFIES: this
    public SportsTeam(String name) {
        players = new ArrayList<Player>();
        this.teamName = name;
    }

    // EFFECTS: if newMember is not already on the team, adds newMember to this.teamMembers
    // and returns true. Returns false if newMember is already on the team.
    public boolean addPlayer(Player newMember) {
        return false; // STUB
    }

    // EFFECTS: prints a list of players on the team
    public void printPlayers() {
        // STUB
    }


}



//- As a user, I want to be able to create a team for a specified sport
//        - As a user, I want to be able to add a player to my team
//        - As a user, I want to be able to view a list of the players on my team
//        - As a user, I want to be able to select a player and view their contract
//        - As a user, I want to be able to view a list of the contracts of players on my team