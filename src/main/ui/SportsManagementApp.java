package ui;

import model.Contract;
import model.Player;
import model.SportsTeam;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// Sports salary manager application
public class SportsManagementApp extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private static final String JSON_STORE = "./data/team.json";
    private SportsTeam team;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JPanel teamList;

    // EFFECTS: runs the sports manager application
    // TODO
    public SportsManagementApp() throws FileNotFoundException {
        initFields();
        initGraphics();
    }

    // MODIFIES: this
    // EFFECTS: initializes input and read/write objects
    void initFields() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        teamList = new JPanel();
    }

    // MODIFIES: this
    // EFFECTS: TODO
    void initGraphics() {
        setTitle("Sports Management App");
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));

        if (team == null) {
            initTeam();
        }
        runSportsManager();

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // TODO
    void runSportsManager() {
        initPersistence();
        viewTeam();
        displayMenu();
    }

    // TODO
    private void initPersistence() {
        JMenuBar persistence = new JMenuBar();
        JMenu saveLoadMenu = new JMenu("Save/Load");
        JMenuItem save = new JMenuItem();
        save.addActionListener(e -> saveTeam());
        save.setText("Save");
        saveLoadMenu.add(save);
        JMenuItem load = new JMenuItem();
        load.addActionListener(e -> loadTeam());
        load.setText("Load");
        saveLoadMenu.add(load);
        persistence.add(saveLoadMenu);
        setJMenuBar(persistence);
    }

    // TODO
    private void viewTeam() {
        teamList.removeAll();
        JLabel temp;
        for (Player player : team.getPlayers()) {
            temp = new JLabel();
            temp.setText("Name: " + player.getName() + ", Age: " + player.getAge());
            JButton viewContract = new JButton("View Contract");
            viewContract.addActionListener(e -> viewPlayerContract(player));
            teamList.add(temp);
            teamList.add(viewContract);
        }
        JLabel image = new JLabel(new ImageIcon("data/splashimage.jpg"));
        teamList.add(image);
        add(teamList);
        refreshTeamList();
    }

    // MODIFIES: this, TODO
    // EFFECTS: if user chooses to load from file, loads team from file,
    // otherwise gets team information from the user and initializes team.
    void initTeam() {
        int loadTeam = JOptionPane.showConfirmDialog(null,
                "Would you like to load your team?");

        if (loadTeam == JOptionPane.NO_OPTION) {
            JTextField nameInput = new JTextField(10);
            String[] sportOptions = {"Hockey", "Football", "Basketball"};
            JPanel createTeam = new JPanel();
            createTeam.setPreferredSize(new Dimension(500, 85));
            JComboBox<String> sport = new JComboBox<>(sportOptions);
            createTeam.add(new JLabel("Enter the name of your team:"));
            createTeam.add(nameInput);
            createTeam.add(new JLabel("Select your team's sport:"));
            createTeam.add(new JComboBox<>(sportOptions));
            JOptionPane.showConfirmDialog(null, createTeam,
                    "Create Team", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

            createTeam(nameInput.getText(), sport.getSelectedItem().toString());
        } else {
            loadTeam();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new team with the teamName and sport .
    private void createTeam(String teamName, String sport) {
        team = new SportsTeam(teamName, sport);
    }

    // TODO
    // EFFECTS: displays menu options to the user
    private void displayMenu() {
        JPanel menuPanel = new JPanel();
        menuPanel.setSize(new Dimension(100, 100));
        JButton add = new JButton("Add Player");
        add.addActionListener(e -> addPlayer());
        JButton viewAllPlayers = new JButton("View All Players");
        viewAllPlayers.addActionListener(e -> viewTeam());
        JButton viewAllContracts = new JButton("View All Contracts");
        viewAllContracts.addActionListener(e -> viewAllPlayerContracts());
        menuPanel.add(add);
        menuPanel.add(viewAllPlayers);
        menuPanel.add(viewAllContracts);
        add(menuPanel, BorderLayout.SOUTH);
    }

    // TODO
    public void addPlayer() {
        addPlayerPopup();
        viewTeam();
        refreshTeamList();
    }

    // TODO
    private void refreshTeamList() {
        teamList.setVisible(false);
        teamList.setVisible(true);
    }

    // MODIFIES: this, TODO
    // EFFECTS: adds a new player to the team
    private void addPlayerPopup() {
        JTextField nameInput = new JTextField(10);
        JTextField ageInput = new JTextField(5);
        JPanel createPlayer = new JPanel();
        createPlayer.setPreferredSize(new Dimension(350, 100));
        createPlayer.add(new JLabel("Enter the players name:"));
        createPlayer.add(nameInput);
        createPlayer.add(new JLabel("Enter the players age (years):"));
        createPlayer.add(ageInput);
        JOptionPane.showConfirmDialog(null, createPlayer,
                "Add Player", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        Contract contract;
        contract = signContract();

        Player player = new Player(nameInput.getText(), Integer.parseInt(ageInput.getText()), contract);
        if (!team.addPlayer(player)) {
            String message = "Cannot add " + nameInput.getText() + " to the team."
                    + nameInput.getText() + "'s contract puts team " + team.getTeamName() + " over the salary cap";
            JOptionPane.showMessageDialog(null, message,
                    "Invalid Contract", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this, TODO
    // EFFECTS: creates a new contract based on user input and returns that contract
    private Contract signContract() {
        Contract contract;
        JTextField lengthInput = new JTextField(5);
        JTextField salaryInput = new JTextField(10);
        JPanel signContract = new JPanel();
        signContract.setPreferredSize(new Dimension(350, 100));
        signContract.add(new JLabel("Enter the length of the players contract (years):"));
        signContract.add(lengthInput);
        signContract.add(new JLabel("Enter the yearly salary of the players contract ($):"));
        signContract.add(salaryInput);
        JOptionPane.showConfirmDialog(null, signContract,
                "Sign Contract", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        contract = new Contract(Integer.parseInt(salaryInput.getText()), Integer.parseInt(lengthInput.getText()));
        return contract;
    }

    // MODIFIES: this
    // EFFECTS: TODO, prompts user to select a player and, if the player exists on the team, asks the user to input
    // new contract details. if the user chooses to change the player's salary, prompts them to enter a new value.
    // If the salary is invalid (takes the team over the salary cap), continues to prompt the user until they
    // enter a valid salary and then extends the players contract, and prints the players contract.
    private void extendPlayer(Player player) {
        JTextField lengthInput = new JTextField(5);
        JTextField salaryInput = new JTextField(10);
        JPanel extendContract = new JPanel();

        int changeSalary = JOptionPane.showConfirmDialog(null,
                "Would you like to change " + player.getName() + "'s salary from "
                        + player.getContract().getSalary() + "?");
        extendContract.add(new JLabel("Enter the number of years to extend " + player.getName() + "'s contract:"));
        extendContract.add(lengthInput);

        if (changeSalary == JOptionPane.YES_OPTION) {
            extendContract.add(new JLabel("Enter " + player.getName() + "'s new yearly salary:"));
            extendContract.add(salaryInput);
            JOptionPane.showConfirmDialog(null, extendContract,
                    "Extend Contract", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            int salary = validateSalary(player, Integer.parseInt(salaryInput.getText()));
            player.getContract().extendContract(salary, Integer.parseInt(lengthInput.getText()));
        } else {
            JOptionPane.showConfirmDialog(null, extendContract,
                    "Extend Contract", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            player.getContract().extendContract(Integer.parseInt(lengthInput.getText()));
        }
        viewPlayerContract(player);
        refreshTeamList();
    }

    // TODO
    private int validateSalary(Player player, int salary) {
        int maxSalary = team.getSalaryCap() - team.getTeamSalary() + player.getContract().getSalary();
        while (salary > maxSalary) {
            salary = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Invalid Salary Please Try Again:"));
        }
        return salary;
    }

    // REQUIRES: player is not null, TODO
    // EFFECTS: prints the contract information of a player
    private void viewPlayerContract(Player player) {
        teamList.removeAll();
        ImageIcon imageIcon1 = new ImageIcon(new ImageIcon("data/money.png")
                .getImage().getScaledInstance(100, 120, Image.SCALE_DEFAULT));
        JLabel imageLabel1 = new JLabel(imageIcon1);
        teamList.add(imageLabel1);
        JLabel temp = new JLabel();
        temp.setText(player.getName() + ": Salary: $" + player.getContract().getSalary()
                + ", Years: " + player.getContract().getYears());
        JButton extendContract = new JButton("Extend Contract");
        extendContract.addActionListener(e -> extendPlayer(player));
        teamList.add(temp);
        teamList.add(extendContract);
        ImageIcon imageIcon2 = new ImageIcon(new ImageIcon("data/money.png")
                .getImage().getScaledInstance(100, 120, Image.SCALE_DEFAULT));
        JLabel imageLabel2 = new JLabel(imageIcon2);
        teamList.add(imageLabel2);
        refreshTeamList();
    }

    // TODO
    // EFFECTS: prints the contract information for all players on the team
    private void viewAllPlayerContracts() {
        teamList.removeAll();
        JLabel summary = new JLabel();
        for (Player player : team.getPlayers()) {
            viewPlayerContract(player);
        }
        summary.setText(team.getTeamName() + " has spent $" + team.getTeamSalary()
                + " of the $" + team.getSalaryCap() + " " + team.getSport() + " salary cap.");
        teamList.add(summary);
        add(teamList);
        refreshTeamList();
    }

    // TODO
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

    // MODIFIES: this, TODO
    // EFFECTS: loads team from file
    private void loadTeam() {
        try {
            team = jsonReader.read();
            viewTeam();
            refreshTeamList();
            System.out.println("Loaded team " + team.getTeamName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (JSONException e) {
            System.out.println("Could not load team from file: " + JSON_STORE + "\n");
            initTeam();
        }
    }

    // EFFECTS: Monitors when the close button is clicked and prompts the user to save their team when that occurs.
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            int exit = JOptionPane.showOptionDialog(null, "Do you want to save your team?",
                    "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, null, null);
            if (exit == JOptionPane.YES_OPTION) {
                saveTeam();
            }
            System.exit(0);
        }
    }
}
