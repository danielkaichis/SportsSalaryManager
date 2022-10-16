package ui;

import model.Contract;
import model.Player;
import model.SportsTeam;

import java.util.Scanner;

public class SportsManagementApp {
    private SportsTeam team;
    private Scanner input;

    // EFFECTS: runs the sports manager application
    public SportsManagementApp() {
        runSportsManager();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runSportsManager() {
        boolean keepRunning = true;
        String command = null;
        String teamName;
        String sport = "";

        init();

        while (keepRunning) {
            System.out.println("Enter the name of your team: ");
            teamName = input.next();
            displaySportMenu();
            while (!createTeam(teamName, sport)) {
                sport = input.next();
            }

            displayMenu();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                keepRunning = false;
            } else {
                proccessCommand(command);
            }
        }
        System.out.println("Thank you for using this Salary Manager Application! \nGoodybe!");
    }

    void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // MODIFIES: this
    // EFFECTS: creates a new team using the users input and returns true if successful. returns false if users input
    // for sport is invalid
    private boolean createTeam(String teamName, String sport) {
        if (sport.equals("h")) {
            sport = "hockey";
        } else if (sport.equals("f")) {
            sport = "football";
        } else if (sport.equals("b")) {
            sport = "basketball";
        } else {
            System.out.println("Invalid input");
            return false;
        }
        team = new SportsTeam(teamName, sport);
        return true;
    }

    private void displaySportMenu() {
        System.out.println("\nSelect sport from: ");
        System.out.println("\nh -> hockey");
        System.out.println("\nf -> football");
        System.out.println("\nb -> basketball");
    }

    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("a -> Add player");
        System.out.println("e -> Extend one players contract");
        System.out.println("l -> View a list of players");
        System.out.println("v -> View the contract of one player");
        System.out.println("c -> View a list of the contracts of all players");
        System.out.println("q -> Quit");
    }

    private void proccessCommand(String command) {
        if (command.equals("a")) {
            addPlayer();
        } else if (command.equals("e")) {
            extendPlayer();
        } else if (command.equals("l")) {
            viewPlayers();
        } else if (command.equals("v")) {
            viewPlayerContract();
        } else if (command.equals("c")) {
            viewAllPlayerContracts();
        } else {
            System.out.println("Selection not valid");
        }
    }

    private void addPlayer() {
        Player player;
        Contract contract;
        System.out.println("\nEnter the players name: ");
        String name = input.next();
        System.out.println("\nEnter the players age: ");
        int age = input.nextInt();
        contract = signContract();
        player = new Player(contract, name, age);
        if (!team.addPlayer(player)) {
            System.out.println("Cannot add " + name + " to the team");
            System.out.println(name + "'s contract puts team " + team.getTeamName() + " over the salary cap");
        }
    }

    private Contract signContract() {
        Contract contract;
        System.out.println("\nEnter the length of the players contract: ");
        int length = input.nextInt();
        System.out.println("\nEnter the yearly salary of the players contract: ");
        int salary = input.nextInt();
        contract = new Contract(salary, length);
        return contract;
    }

    private void extendPlayer() {
        Player player;
        player = selectPlayer();
        if (player != null) {
            System.out.println("\nWould you like to change " + player.getName() + "'s salary from "
                    + player.getContract().getSalary() + "? [y/n]");
            String response = input.next();
            response.toLowerCase();
            System.out.println("\nYears to extend " + player.getName() + "'s contract by: ");
            int years = input.nextInt();
            if (response.equals("y")) {
                System.out.println("\nNew salary: ");
                int salary = input.nextInt();
                player.getContract().extendContract(salary, years);
            } else {
                player.getContract().extendContract(years);
            }
            System.out.println(player.getName() + "'s new contract: ");
            viewPlayerContract(player);
        }
    }

    private void viewPlayers() {

    }

    private void viewPlayerContract() {
        Player player;
        player = selectPlayer();
        if (player != null) {
            viewPlayerContract(player);
        }
    }

    private void viewPlayerContract(Player player) {
        System.out.println("\nSalary: " + player.getContract().getSalary());
        System.out.println("\nYears: " + player.getContract().getYears());
    }

    private void viewAllPlayerContracts() {

    }


    private Player selectPlayer() {
        System.out.println("\nEnter the name of the player: ");
        String playerName = input.next();
        for (Player player : team.getPlayers()) {
            if (player.getName().equals(playerName)) {
                return player;
            }
        }
        System.out.println("\nCould not find player " + playerName + " on the team.");
        return null;
    }
}
