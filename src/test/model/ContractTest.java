package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

// Test for all methods in the Contract class, also includes fields to effectively test these methods
public class ContractTest {
    private Contract testContract;

    @BeforeEach
    void runBefore() {
        testContract = new Contract(100000, 5);
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
}
