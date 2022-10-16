package model;

public class Contract {
    private int salary;
    private int years;

    // REQUIRES: salary >= 0, years >= 0;
    // MODIFIES: this
    public Contract(int salary, int years) {
        this.salary = salary;
        this.years = years;
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

}
