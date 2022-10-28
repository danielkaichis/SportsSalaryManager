package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a player on a sports team with a name, age, and contract
public class Player implements Writable {
    private String name;
    private int age;
    private Contract contract;

    // MODIFIES: this
    // EFFECTS: sets the players contract, name, and age
    public Player(String name, int age, Contract contract) {
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

    // Based on the supplied workroom example for CPSC 210
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("age", age);
        json.put("contract", contract.toJson());
        return json;
    }
}
