package catering.businesslogic.event;

import catering.businesslogic.menu.Menu;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.util.ArrayList;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.concurrent.atomic.AtomicReference;

public class ServiceInfo implements EventItemInfo {
    private int id;
    private String name;
    private Date date;
    private Time timeStart;
    private Time timeEnd;
    private int participants;

    private Menu menu;

    public ServiceInfo(String name) {
        this.name = name;
    }

    public ServiceInfo () {}

    public int getId(){
        return this.id;
    }


    public Menu getMenu(){
        return this.menu;
    }

    public void setMenu(Menu m){
        this.menu = m;
    }

    public boolean isAssociated(User user) {
        return true;
    }


    public String toString() {
        return name + ": " + date + " (" + timeStart + "-" + timeEnd + "), " + participants + " pp.";
    }

    // STATIC METHODS FOR PERSISTENCE

    public static ArrayList<ServiceInfo> loadServiceInfoForEvent(int event_id) {
        ArrayList<ServiceInfo> result = new ArrayList<>();
        String query = "SELECT id, name, service_date, time_start, time_end, partecipants " +
                "FROM Services WHERE event_id = " + event_id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String s = rs.getString("name");
                ServiceInfo serv = new ServiceInfo(s);
                serv.id = rs.getInt("id");
                serv.date = rs.getDate("service_date");
                serv.timeStart = rs.getTime("time_start");
                serv.timeEnd = rs.getTime("time_end");
                serv.participants = rs.getInt("partecipants");
                result.add(serv);
            }
        });

        return result;
    }


    public static ServiceInfo loadServiceById(int id) {
        ServiceInfo service = new ServiceInfo();
        String query = "SELECT * FROM services WHERE id=" + id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                service.name = rs.getString("name");
                service.id = rs.getInt("id");
                service.date = rs.getDate("service_date");
                service.timeStart = rs.getTime("time_start");
                service.timeEnd = rs.getTime("time_end");
                service.menu = Menu.getMenuById(rs.getInt("id_menu"));
            }
        });
        return service;
    }


}
