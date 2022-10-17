package model;

// Represents a player on a sports team with a name, age, and contract
public class Player {
    private String name;
    private int age;
    private Contract contract;

    // MODIFIES: this
    // EFFECTS: sets the players contract, name, and age
    public Player(Contract contract, String name, int age) {
        this.contract = contract;
        this.name = name;
        this.age = age;
    }

    public Contract getContract() {
        return this.contract;
    }

    public int getAge() {
        return this.age;
    }

    public String getName() {
        return this.name;
    }
}
