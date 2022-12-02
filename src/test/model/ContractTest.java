package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Test for all methods in the Contract class, also includes fields to effectively test these methods
public class ContractTest {
    private Contract testContract;

    @BeforeEach
    void runBefore() {
        testContract = new Contract(100000, 5);
        EventLog.getInstance().clear();
    }

    @Test
    void testConstructor() {
        assertEquals(100000, testContract.getSalary());
        assertEquals(5, testContract.getYears());
    }

    @Test
    void testExtendContractWithoutSalary() {
        assertEquals(9, testContract.extendContract(4));
        assertEquals(9, testContract.getYears());
        assertEquals(100000, testContract.getSalary());
    }

    @Test
    void testExtendContractWithNewSalary() {
        assertEquals(10, testContract.extendContract(200000, 5));
        assertEquals(10, testContract.getYears());
        assertEquals(200000, testContract.getSalary());
    }

    @Test
    void testExtendContractLog() {
        testContract.extendContract(100000, 5);
        testContract.extendContract(4);
        List<Event> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();

        for (Event next : el) {
            l.add(next);
        }
        assertEquals("Event log cleared.", l.get(0).getDescription());
        assertEquals("Contract extended.", l.get(1).getDescription());
        assertEquals("Contract extended.", l.get(2).getDescription());
    }
}
