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
