package persistence;

import org.json.JSONObject;

// Based on the supplied workroom example for CPSC 210, https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
