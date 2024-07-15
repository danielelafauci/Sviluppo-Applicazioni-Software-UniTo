package catering.businesslogic.shift;

import catering.businesslogic.recipe.Recipe;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.*;

public class Shift {
    private int id;
    private Date date;
    private String place;
    private Time start;
    private Time end;

    private static Map<Integer, Shift> all = new HashMap<>();

    public int getId() {
        return id;
    }

    public static Map<Integer,Shift> loadAllShifts() {
        Map<Integer, Shift> shifts = new HashMap<>();
        String query = "SELECT * FROM Shifts";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                Shift s = new Shift();
                s.id = rs.getInt("id");
                s.date = rs.getDate("date");
                s.start = rs.getTime("h_start");
                s.end = rs.getTime("h_end");
                s.place = rs.getString("place");
                shifts.put(s.id,s);
            }
        });
        return shifts;
    }
    public static ArrayList<Shift> getAllShifts() {
        return new ArrayList<Shift>(all.values());
    }
    @Override
    public String toString() {
        return "\n\t\tid: " + id +
                ", date: " + date +
                ", place: " + place + '\'' +
                ", start: " + start +
                ", end: " + end;
    }
}
