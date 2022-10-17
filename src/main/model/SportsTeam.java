package model;

import java.util.ArrayList;
import java.util.List;

// Represents a sports team having a list of any number of players, a name, the sport the team plays, a salary cap
// (aka maximum salary), and a total salary (sum of players salaries).
public class SportsTeam {
    private List<Player> players;
    private String teamName;
    private String sport;
    private int salaryCap;
    private int teamSalary;
    private static final int HOCKEY_SALARY_CAP = 81500000;
    public static final int FOOTBALL_SALARY_CAP = 208200000;
    public static final int BASKETBALL_SALARY_CAP = 134000000;

    // REQUIRES: sport is one of "hockey", "football", or "basketball"
    // MODIFIES: this
    // EFFECTS: initializes empty array of players, sets teamName and sport, and sets the salary cap based on which
    // sport the team plays
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

    // MODIFIES: this
    // EFFECTS: calculates and returns the sum of the salaries of the players on the team. This calculation is done
    // each time this method is called in case contracts are extended in other classes
    public int getTeamSalary() {
        this.teamSalary = 0;
        for (Player player : players) {
            teamSalary += player.getContract().getSalary();
        }
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