package catering.businesslogic.kitchentask;

import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.recipe.KitchenTask;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.user.User;
import catering.persistence.BatchUpdateHandler;
import catering.persistence.PersistenceManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SummarySheet {

    private int id;
    private ArrayList<Task> tasks;
    private User creator;
    private ServiceInfo serviceAssigned;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int getTaskIndex(Task task) {
        return tasks.indexOf(task);
    }

    public ServiceInfo getServiceAssigned() {
        return serviceAssigned;
    }

    public boolean isCreator(User user) {
        return (user.getId() == creator.getId());
    }

    public boolean containsTask(Task task) {
        return this.tasks.contains(task);
    }

    public SummarySheet(ServiceInfo ser, User currentUser) {
        this.creator = currentUser;
        this.serviceAssigned = ser;
        tasks = new ArrayList<>();
    }

    public void initSummarySheet() {
        Menu menu = this.serviceAssigned.getMenu();
        ArrayList<Recipe> allRecipes = menu.getAllRecipes();
        for (Recipe recipe : allRecipes) {
            tasks.add(new Task(recipe));
        }
    }

    public void resetSummarySheet() {
        this.tasks = new ArrayList<>();
        initSummarySheet();
    }
    public Task addTask(KitchenTask kt) {
        Task task = new Task(kt);
        tasks.add(task);
        return task;
    }

    public void addTask(Task task, int position) {
        this.tasks.add(position, task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    public static void saveSummarySheet(SummarySheet summarySheet, Menu m) {
        String summarySheetInsert = "INSERT INTO summarysheets (id_chef, id_service) VALUES (?, ?);";
        int[] result = PersistenceManager.executeBatchUpdate(summarySheetInsert, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, summarySheet.creator.getId());
                ps.setInt(2, summarySheet.serviceAssigned.getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                if (count == 0) {
                    summarySheet.id = rs.getInt(1);
                }
            }
        });
        if (result[0] > 0) {
            // SAVING ALL summarySheet'S TASKS TOO
            Task.saveAllNewTasks(summarySheet, m);
        }
    }



    public static void sortTasks(SummarySheet summarySheet) {
        String query = "UPDATE tasks SET position = ? WHERE id = ?";
        PersistenceManager.executeBatchUpdate(query, summarySheet.getTasks().size(), new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, batchCount);
                ps.setInt(2, summarySheet.getTasks().get(batchCount).getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {

            }
        });

    }

    public static void setAssignment(Task task) {
        String query = "UPDATE tasks SET " +
                "`id_shift` = " + task.getShiftAssigned().getId() + ", " +
                "`id_cook` = " + (task.getCookAssigned() == null ? "null" : task.getCookAssigned().getId()) + "" +
                " WHERE `id` = " + task.getId();
        PersistenceManager.executeUpdate(query);
    }

    public static void setTaskDetails(Task task) {
        String query =  "UPDATE tasks SET " +
                "`timeEstimate` = " + task.getTimeEstimate() + ", " +
                "`quantity` = " + task.getQuantity() + "" +
                " WHERE `id` = " + task.getId();
        PersistenceManager.executeUpdate(query);
    }

    @Override
    public String toString() {
        return "-SUMMARY SHEET-" +
                "\n| ID: " +  id +
                "\n| Creator: " + creator +
                "\n| Tasks: " + tasks.toString()+
                "\n| Service Assigned: " +  serviceAssigned +
                "\n--------------";
    }



}
