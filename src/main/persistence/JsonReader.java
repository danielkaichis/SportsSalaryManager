package persistence;

import model.Player;
import model.SportsTeam;
import model.Contract;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// General Code based on the supplied workroom example for CPSC 210,
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Code specific to this application may be similar to this source as well but has been modified for the purpose
// of this project.

// Represents a reader that reads a sports team from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads sports team from file and returns it;
    // throws IOException if an error occurs reading data from file
    public SportsTeam read() throws IOException, JSONException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSportsTeam(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // MODIFIES: team
    // EFFECTS: parses team from JSON object and returns it
    private SportsTeam parseSportsTeam(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String sport = jsonObject.getString("sport");
        SportsTeam team = new SportsTeam(name, sport);
        addPlayers(team, jsonObject);
        return team;
    }

    // MODIFIES: team
    // EFFECTS: parses players from JSON object and adds them to team
    private void addPlayers(SportsTeam team, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(team, nextPlayer);
        }
    }

    // MODIFIES: team
    // EFFECTS: parses player from JSON object and adds it to team
    private void addPlayer(SportsTeam team, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int age = jsonObject.getInt("age");
        JSONObject contractObject = jsonObject.getJSONObject("contract");
        Contract contract = parseContract(contractObject);
        Player player = new Player(name, age, contract);
        team.addPlayer(player);
    }

    // EFFECTS: parses contract from JSON object and returns it
    private Contract parseContract(JSONObject jsonObject) {
        int salary = jsonObject.getInt("salary");
        int years = jsonObject.getInt("years");
        return new Contract(salary, years);
    }
}
