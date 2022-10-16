package model;

import java.util.ArrayList;
import java.util.List;

public class SportsTeam {
    private List<Player> players;
    private String teamName;
    private String sport;
    private int salaryCap;
    private int teamSalary;
    private static final int HOCKEY_SALARY_CAP = 81500000;
    public static final int FOOTBALL_SALARY_CAP = 208200000;
    public static final int BASKETBALL_SALARY_CAP = 134000000;

    // MODIFIES: this
    public SportsTeam(String name, String sport) {
        players = new ArrayList<Player>();
        this.teamName = name;
        this.sport = sport;

        if (sport.equals("hockey")) {
            this.salaryCap = HOCKEY_SALARY_CAP;
        } else if (sport.equals("football")) {
            this.salaryCap = FOOTBALL_SALARY_CAP;
        } else {
            this.salaryCap = BASKETBALL_SALARY_CAP;
        }
    }

    // MODIFIES: this
    // EFFECTS: if newMember is not already on the team and adding their salary keeps the team below the salary cap,
    // adds newMember to this.teamMembers, adds their salary to teamSalary, and returns true.
    // Returns false if newMember is already on the team or newMembers salary would put the
    // teams total salary above the salary cap.
    public boolean addPlayer(Player newPlayer) {
        if (!players.contains(newPlayer) && (this.teamSalary + newPlayer.getContract().getSalary()) <= salaryCap) {
            players.add(newPlayer);
            this.teamSalary += newPlayer.getContract().getSalary();
            return true;
        } else {
            return false;
        }
    }

    public int getTeamSalary() {
        return this.teamSalary;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public String getSport() {
        return this.sport;
    }

    public int getSalaryCap() {
        return this.salaryCap;
    }
}



//- As a user, I want to be able to create a team for a specified sport
//        - As a user, I want to be able to add a player to my team
//        - As a user, I want to be able to view a list of the players on my team
//        - As a user, I want to be able to select a player and view their contract
//        - As a user, I want to be able to view a list of the contracts of players on my team