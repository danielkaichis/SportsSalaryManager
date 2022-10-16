package ui;

import model.Contract;
import model.Player;
import model.SportsTeam;

public class SportsManagementApp {
    private SportsTeam team;

    public SportsManagementApp() {
        runSportsManager();
    }

    private void runSportsManager() {

    }

    private Player selectPlayer() {
        return new Player(new Contract(0, 0), "Joe", 0);
    }
}
