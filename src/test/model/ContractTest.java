package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;



public class ContractTest {
    private Contract testContract;

    @BeforeEach
    void runBefore() {
        testContract = new Contract(100000, 5);
    }

    @Test
    void testConstructor() {
        //STUB
    }

    @Test
    void testExtendContractWithoutSalary() {
        //STUB
    }

    @Test
    void testExtendContractWithNewSalary() {
        //STUB
    }
}
