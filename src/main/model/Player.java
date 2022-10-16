package model;

public class Player {
    private String name;
    private int age;
    private Contract contract;

    // MODIFIES: this
    public Player(Contract contract, String name, int age) {
        this.contract = contract;
        this.name = name;
        this.age = age;
    }

    // REQUIRES: length >= 0
    // MODIFIES: this
    // EFFECTS: extends the length of the players contract and returns the new length
    public int extendContract(int length) {
        return 0; //STUB
    }

    // REQUIRES: length >= 0, newSalary >= 0
    // MODIFIES: this
    // EFFECTS: extends players contract length, updates their salary to newSalary, and returns the new contract length
    public int extendContract(int newSalary, int length) {
        return 0; // STUB
    }

    // EFFECTS: prints the players contract
    public void printContract() {
        //STUB
    }

    // EFFECTS: prints the all of the players information contained in this class
    public void printPlayerInformation() {
        // STUB
    }
}
