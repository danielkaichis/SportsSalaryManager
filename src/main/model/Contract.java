package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents the contract of a player on a sports team with a salary and number of years the contract will last
public class Contract implements Writable {
    private int salary;
    private int years;

    // REQUIRES: salary >= 0, years >= 0;
    // MODIFIES: this
    // EFFECTS: sets contract salary and years
    public Contract(int salary, int years) {
        this.salary = salary;
        this.years = years;
    }

    // REQUIRES: length >= 0
    // MODIFIES: this
    // EFFECTS: extends the length of the players contract by length and returns the new length
    public int extendContract(int length) {
        this.years += length;
        return this.years;
    }

    // REQUIRES: length >= 0, newSalary >= 0
    // MODIFIES: this
    // EFFECTS: extends players contract length, updates their salary to newSalary, and returns the new contract length
    public int extendContract(int newSalary, int length) {
        extendContract(length);
        this.salary = newSalary;
        return this.years;
    }

    public int getSalary() {
        return this.salary;
    }

    public int getYears() {
        return this.years;
    }

    // Based on the supplied workroom example for CPSC 210
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("salary", salary);
        json.put("years", years);
        return json;
    }
}
