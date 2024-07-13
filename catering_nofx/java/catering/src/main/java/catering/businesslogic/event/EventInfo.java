package catering.businesslogic.event;

import java.util.ArrayList;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import catering.businesslogic.CatERing;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

public class EventInfo implements EventItemInfo {
    private int id;
    private String name;
    private Date dateStart;
    private Date dateEnd;
    private int participants;
    private User organizer;

    private User Chef;

    private ArrayList<ServiceInfo> services;

    public EventInfo(String name) {
        this.name = name;
        id = 0;
    }

    public int getId() {
        return id;
    }

    public ArrayList<ServiceInfo> getServices() {
        return this.services;
    }

    public String toString() {
        return name + ": " + dateStart + "-" + dateEnd + ", " + participants;
    }

    // STATIC METHODS FOR PERSISTENCE

    public static ArrayList<EventInfo> loadAllEventInfo() {
        ArrayList<EventInfo> all = new ArrayList<>();
        String query = "SELECT * FROM events";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String n = rs.getString("name");
                EventInfo e = new EventInfo(n);
                e.id = rs.getInt("id");
                e.dateStart = rs.getDate("date_start");
                e.dateEnd = rs.getDate("date_end");
                e.participants = rs.getInt("expected_participants");
                System.out.println(e.toString());
                all.add(e);
            }
        });

        for (EventInfo e : all) {
            e.services = ServiceInfo.loadServiceInfoForEvent(e.id);
        }
        return all;
    }

    public boolean providesService(ServiceInfo service) {
        for(ServiceInfo se : services){
            if(se.equals(service))
                return true;
        }
        return false;
    }

    public void setChef(User chef){
        this.Chef = chef;
    }

    public User getChef(){
        // return this.Chef;
        return CatERing.getInstance().getUserManager().getCurrentUser();
    }
}
