package ui;

import model.Contract;
import model.Event;
import model.EventLog;
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
    private static final int GAP = 20;

    private static final String JSON_STORE = "./data/team.json";
    private SportsTeam team;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JPanel mainPanel;

    // MODIFIES: this
    // EFFECTS: runs the sports manager application by initializing fields and graphics
    public SportsManagementApp() {
        initFields();
        initGraphics();
    }

    // MODIFIES: this
    // EFFECTS: initializes input and read/write objects
    void initFields() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    }

    // MODIFIES: this
    // EFFECTS: sets JFrame values, inits team, and runs the application
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

    // MODIFIES: this
    // EFFECTS: initializes persistence gui, displays the team on the landing page, and
    // displays the menu buttons to the user
    private void runSportsManager() {
        initPersistence();
        viewTeam();
        displayMenu();
    }

    // MODIFIES: this
    // EFFECTS: adds menu bar to this with save and load buttons for the user to save and load their team
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

    // MODIFIES: this
    // EFFECTS: if user chooses to load from file, loads team from file,
    // otherwise gets team information from the user and initializes team.
    private void initTeam() {
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
    // EFFECTS: creates a new team with the teamName and sport.
    private void createTeam(String teamName, String sport) {
        team = new SportsTeam(teamName, sport);
    }

    // MODIFIES: this
    // EFFECTS: clears the mainPanel and prints each player's information to the screen with a button
    // to view each player's contract.
    private void viewTeam() {
        mainPanel.removeAll();
        JLabel temp;
        JPanel tempPanel;
        for (Player player : team.getPlayers()) {
            temp = new JLabel();
            tempPanel = new JPanel();
            tempPanel.setMaximumSize(new Dimension(WIDTH, 50));
            temp.setText("Name: " + player.getName() + ", Age: " + player.getAge());
            tempPanel.add(temp);
            JButton viewContract = new JButton("View Contract");
            viewContract.addActionListener(e -> clearPanelAndViewContract(player));
            tempPanel.add(viewContract);
            JButton removePlayer = new JButton("Remove Player");
            removePlayer.addActionListener(e -> removePlayer(player));
            tempPanel.add(removePlayer);
            mainPanel.add(tempPanel);
        }
        add(mainPanel);
        refreshMainPanel();
    }

    // MODIFIES: this
    // EFFECTS: removes player from team and re-renders team on mainPanel
    private void removePlayer(Player player) {
        team.removePlayer(player);
        viewTeam();
        refreshMainPanel();
    }

    // MODIFIES: this
    // EFFECTS: clears the mainPanel and prints the single player's contract to the screen.
    private void clearPanelAndViewContract(Player player) {
        mainPanel.removeAll();
        viewPlayerContract(player);
    }

    // MODIFIES: this
    // EFFECTS: displays buttons for menu options at the bottom of this
    // Adds actions listeners to each button to perform the corresponding action when the user presses them.
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

    // MODIFIES: this
    // EFFECTS: prompts the user to add a player with a popup, then refreshes the team in mainPanel by calling viewTeam,
    // and refreshes the mainPanel to show the added player.
    public void addPlayer() {
        addPlayerPopup();
        viewTeam();
        refreshMainPanel();
    }

    // MODIFIES: this
    // EFFECTS: refreshes the mainPanel by setting its visibility to false and then true so changes will be rendered.
    private void refreshMainPanel() {
        mainPanel.setVisible(false);
        mainPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: prompts the user to enter player information and adds the new player to the team
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
            String message = "Cannot add " + nameInput.getText() + " to the team. "
                    + nameInput.getText() + "'s contract puts " + team.getTeamName() + " over the salary cap";
            JOptionPane.showMessageDialog(null, message,
                    "Invalid Contract", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
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
    // REQUIRES: player is not null
    // EFFECTS: prompts the user to change the inputted players contract. if the user chooses to change the
    // player's salary, prompts them to enter a new value. If the salary is invalid (takes the team over
    // the salary cap), continues to prompt the user until they enter a valid salary and
    // then extends the players contract, and prints the players contract to the screen.
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
        mainPanel.removeAll();
        viewPlayerContract(player);
        refreshMainPanel();
    }

    // REQUIRES: player is not null, salary is positive
    // EFFECTS: if players new salary puts the team over the salary cap, prompts the user to enter a new salary with
    // a popup input and returns the new salary once a valid salary is inputted.
    private int validateSalary(Player player, int salary) {
        int maxSalary = team.getSalaryCap() - team.getTeamSalary() + player.getContract().getSalary();
        while (salary > maxSalary) {
            salary = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Invalid Salary Please Try Again:"));
        }
        return salary;
    }

    // MODIFIES: this
    // REQUIRES: player is not null
    // EFFECTS: adds the contract information of a player to the mainPanel and refreshes mainPanel
    // so changes are reflected to the user.
    private void viewPlayerContract(Player player) {
        JPanel panel = new JPanel();
        panel.setMaximumSize(new Dimension(WIDTH, 50));
        ImageIcon imageIcon1 = new ImageIcon(new ImageIcon("images/money.png")
                .getImage().getScaledInstance(25, 40, Image.SCALE_DEFAULT));
        JLabel imageLabel1 = new JLabel(imageIcon1);
        panel.add(imageLabel1);
        JLabel temp = new JLabel();
        temp.setText(player.getName() + ": Salary: $" + player.getContract().getSalary()
                + ", Years: " + player.getContract().getYears());
        JButton extendContract = new JButton("Extend Contract");
        extendContract.addActionListener(e -> extendPlayer(player));
        panel.add(temp);
        panel.add(extendContract);
        ImageIcon imageIcon2 = new ImageIcon(new ImageIcon("images/money.png")
                .getImage().getScaledInstance(25, 40, Image.SCALE_DEFAULT));
        JLabel imageLabel2 = new JLabel(imageIcon2);
        panel.add(imageLabel2);
        mainPanel.add(panel);
        refreshMainPanel();
    }

    // MODIFIES: this
    // EFFECTS: adds the contract information for all players on the team to mainPanel and refreshes mainPanel
    // so changes are reflected to the user.
    private void viewAllPlayerContracts() {
        mainPanel.removeAll();
        JPanel summaryPanel = new JPanel();
        JLabel summary = new JLabel();
        JLabel gap = new JLabel();
        for (Player player : team.getPlayers()) {
            viewPlayerContract(player);
        }
        gap.setPreferredSize(new Dimension(WIDTH, GAP));
        summaryPanel.add(gap);
        summary.setText(team.getTeamName() + " has spent $" + team.getTeamSalary()
                + " of the $" + team.getSalaryCap() + " " + team.getSport() + " salary cap.");
        summaryPanel.add(summary);
        mainPanel.add(summaryPanel, BorderLayout.PAGE_END);
        refreshMainPanel();
    }

    // EFFECTS: saves the team to file. If file is not found, alerts the user.
    private void saveTeam() {
        try {
            jsonWriter.open();
            jsonWriter.write(team);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null,
                    "Saved " + team.getTeamName() + " to " + JSON_STORE,
                    "Successful Save",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to write to file: " + JSON_STORE,
                    "Unsuccessful Save",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads team from file and displays the team on the mainPanel. If load was invalid,
    // alerts user.
    private void loadTeam() {
        try {
            team = jsonReader.read();
            viewTeam();
            refreshMainPanel();
            JOptionPane.showMessageDialog(null,
                    "Loaded team " + team.getTeamName() + " from " + JSON_STORE,
                    "Successful Load", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to read from file: " + JSON_STORE,
                    "Unsuccessful Load", JOptionPane.ERROR_MESSAGE);
        } catch (JSONException e) {
            JOptionPane.showMessageDialog(null,
                    "Could not load team from file: " + JSON_STORE,
                    "Unsuccessful Load", JOptionPane.ERROR_MESSAGE);
            initTeam();
        }
    }

    // MODIFIES: this
    // Based on code from https://www.clear.rice.edu/comp310/JavaResources/frame_close.html
    // EFFECTS: Monitors when the close button is clicked and prompts the user to save their team when that occurs.
    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            int exit = JOptionPane.showOptionDialog(null, "Do you want to save your team?",
                    "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, null, null);
            if (exit == JOptionPane.YES_OPTION) {
                saveTeam();
            }
            printLog();
            System.exit(0);
        }
    }

    // EFFECTS: prints all Events in EventLog to the console
    public void printLog() {
        EventLog log = EventLog.getInstance();
        for (Event e : log) {
            System.out.println(e.toString());
        }
    }
}
