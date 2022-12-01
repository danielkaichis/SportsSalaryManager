package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

// Code from AlarmSystem, https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e;
    private Date d;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        e = new Event("Player event");   // (1)
        d = Calendar.getInstance().getTime();   // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("Player event", e.getDescription());
        assertEquals(d, e.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Player event", e.toString());
    }

    @Test
    public void testEquals() {
        Event other = null;
        String other2 = "String";
        assertFalse(e.equals(other));
        assertFalse(e.equals(other2));
        assertTrue(e.equals(e));
    }

    @Test
    public void testHashCode() {
        int dateHash = e.getDate().hashCode();
        int descHash = e.getDescription().hashCode();
        assertEquals(13 * dateHash + descHash, e.hashCode());
    }
}