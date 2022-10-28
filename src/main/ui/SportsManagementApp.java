package ui;

import model.Contract;
import model.Player;
import model.SportsTeam;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Sports salary manager application
public class SportsManagementApp {
    private static final String JSON_STORE = "./data/team.json";
    private SportsTeam team;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the sports manager application
    public SportsManagementApp() throws FileNotFoundException {
        runSportsManager();
    }

    // MODIFIES: this
    // EFFECTS: runs the application until the user chooses to quit
    private void runSportsManager() {
        boolean keepRunning = true;
        String command;

        init();

        System.out.println("Would you like to load a team from a file? [y/n]: ");
        String saveInput = input.next();
        if (saveInput.equals("y")) {
            loadTeam();
        } else {
            initTeam();
        }

        while (keepRunning) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                remindUserToSave();
                keepRunning = false;
            } else {
                proccessCommand(command);
            }
        }
        System.out.println("Thank you for using this Salary Manager Application! \nGoodybe!");
    }

    // MODIFIES: this
    // EFFECTS: initializes input and read/write objects
    void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: if user chooses to load from file, loads team from file,
    // otherwise gets team information from the user and initializes team.
    void initTeam() {
        String teamName;
        String sport;
        System.out.println("Enter the name of your team to create: ");
        teamName = input.next();
        displaySportMenu();
        sport = input.next();
        createTeam(teamName, sport);
    }

    // MODIFIES: this
    // EFFECTS: creates a new team using the users input. Continues to prompt user to enter a sport until their
    // input is valid
    private void createTeam(String teamName, String sport) {
        if (sport.equals("h")) {
            sport = "hockey";
        } else if (sport.equals("f")) {
            sport = "football";
        } else if (sport.equals("b")) {
            sport = "basketball";
        } else {
            System.out.println("Invalid input");
            displaySportMenu();
            sport = input.next();
            createTeam(teamName, sport);
        }
        team = new SportsTeam(teamName, sport);
    }

    // EFFECTS: displays sports team menu options to the user
    private void displaySportMenu() {
        System.out.println("\nSelect sport from: ");
        System.out.println("h -> hockey");
        System.out.println("f -> football");
        System.out.println("b -> basketball");
    }

    // EFFECTS: displays menu options to the user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("a -> Add player");
        System.out.println("e -> Extend one players contract");
        System.out.println("p -> View a list of players");
        System.out.println("v -> View the contract of one player");
        System.out.println("c -> View a list of the contracts of all players");
        System.out.println("s -> Save team to file");
        System.out.println("q -> Quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void proccessCommand(String command) {
        if (command.equals("a")) {
            addPlayer();
        } else if (command.equals("e")) {
            extendPlayer();
        } else if (command.equals("p")) {
            viewPlayers();
        } else if (command.equals("v")) {
            viewPlayerContract();
        } else if (command.equals("c")) {
            viewAllPlayerContracts();
        } else if (command.equals("s")) {
            saveTeam();
        } else {
            System.out.println("Selection not valid");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new player to the team
    private void addPlayer() {
        Player player;
        Contract contract;
        System.out.println("\nEnter the players name: ");
        String name = input.next();
        System.out.println("\nEnter the players age (years): ");
        int age = input.nextInt();
        contract = signContract();
        player = new Player(name, age, contract);
        if (!team.addPlayer(player)) {
            System.out.println("Cannot add " + name + " to the team");
            System.out.println(name + "'s contract puts team " + team.getTeamName() + " over the salary cap");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new contract based on user input and returns that contract
    private Contract signContract() {
        Contract contract;
        System.out.println("\nEnter the length of the players contract (years): ");
        int length = input.nextInt();
        System.out.println("\nEnter the yearly salary of the players contract ($): ");
        int salary = input.nextInt();
        contract = new Contract(salary, length);
        return contract;
    }

    // MODIFIES: this
    // EFFECTS: prompts user to select a player and, if the player exists on the team, extends their contract
    private void extendPlayer() {
        Player player;
        player = selectPlayer();
        if (player != null) {
            System.out.println("\nWould you like to change " + player.getName() + "'s salary from "
                    + player.getContract().getSalary() + "? [y/n]");
            String response = input.next();
            System.out.println("\nYears to extend " + player.getName() + "'s contract by: ");
            int years = input.nextInt();
            if (response.equals("y")) {
                int maxSalary = team.getSalaryCap() - team.getTeamSalary() + player.getContract().getSalary();
                System.out.println("\nNew salary (max " + maxSalary + "): ");
                int salary = input.nextInt();
                while (salary > maxSalary) {
                    System.out.println("Invalid Salary, please try again: ");
                    salary = input.nextInt();
                }
                player.getContract().extendContract(salary, years);
            } else {
                player.getContract().extendContract(years);
            }
            viewPlayerContract(player);
        }
    }

    // MODIFIES: this
    // EFFECTS: prints a list of the players on the team
    private void viewPlayers() {
        for (Player player : team.getPlayers()) {
            System.out.println("Name: " + player.getName() + ", Age: " + player.getAge());
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts the user to select a player and prints their contract information
    private void viewPlayerContract() {
        Player player;
        player = selectPlayer();
        if (player != null) {
            viewPlayerContract(player);
        }
    }

    // REQUIRES: player is not null
    // EFFECTS: prints the contract information of a player
    private void viewPlayerContract(Player player) {
        System.out.println(player.getName() + "'s Contract");
        System.out.println("Salary: $" + player.getContract().getSalary()
                + ", Years: " + player.getContract().getYears());
    }

    // EFFECTS: prints the contract information for all players on the team
    private void viewAllPlayerContracts() {
        for (Player player : team.getPlayers()) {
            viewPlayerContract(player);
        }
        System.out.println("Team has spent $" + team.getTeamSalary()
                + " of the $" + team.getSalaryCap() + " " + team.getSport() + " salary cap.");
    }

    // MODIFIES: this
    // EFFECTS: prompts the user to enter the name of a player and returns that player if they are on the team.
    // returns null if the player is not on the team.
    private Player selectPlayer() {
        System.out.println("\nEnter the name of the player: ");
        String playerName = input.next();
        playerName = playerName.toLowerCase();
        for (Player player : team.getPlayers()) {
            if (player.getName().toLowerCase().equals(playerName)) {
                return player;
            }
        }
        System.out.println("\nCould not find player " + playerName + " on the team.");
        return null;
    }

    // EFFECTS: saves the team to file
    private void saveTeam() {
        try {
            jsonWriter.open();
            jsonWriter.write(team);
            jsonWriter.close();
            System.out.println("Saved " + team.getTeamName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads team from file
    private void loadTeam() {
        try {
            team = jsonReader.read();
            System.out.println("Loaded team " + team.getTeamName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (JSONException e) {
            System.out.println("Could not load team from file: " + JSON_STORE + "\n");
            initTeam();
        }
    }

    // EFFECTS: asks the user if they want to save their team, and saves it to a file if they do
    private void remindUserToSave() {
        System.out.println("Would you like to save your team before you quit? [y/n]: ");
        String response = input.next();
        if (response.equals("y")) {
            saveTeam();
        }
    }
}
