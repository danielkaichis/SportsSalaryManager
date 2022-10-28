package persistence;

import model.Contract;
import model.Player;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    // based on the supplied workroom example for CPSC 210
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    protected void checkPlayer(String name, int age, Contract contract, Player player) {
        assertEquals(name, player.getName());
        assertEquals(age, player.getAge());
        checkContract(contract, player.getContract());
    }

    private void checkContract(Contract contract1, Contract contract2) {
         assertEquals(contract1.getSalary(), contract2.getSalary());
         assertEquals(contract1.getYears(), contract2.getYears());
    }
}
