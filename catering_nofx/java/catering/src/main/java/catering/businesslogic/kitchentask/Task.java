package catering.businesslogic.kitchentask;

import catering.businesslogic.menu.Menu;
import catering.businesslogic.recipe.KitchenTask;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;
import catering.persistence.BatchUpdateHandler;
import catering.persistence.PersistenceManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Task {

    private int id;
    private boolean completed;
    private int timeEstimate;
    private String quantity;
    private Shift shiftAssigned;
    private User cookAssigned;
    private KitchenTask ktAssigned;

    public Task(KitchenTask tkAssigned) {
        this.ktAssigned = tkAssigned;
    }

    public void assignTask(Shift shift, User cook) {
        this.shiftAssigned = shift;
        this.cookAssigned = cook;
    }

    public KitchenTask getKtAssigned() {
        return ktAssigned;
    }

    public Shift getShiftAssigned() {
        return shiftAssigned;
    }

    public User getCookAssigned() {
        return cookAssigned;
    }



    public int getTimeEstimate() {
        return timeEstimate;
    }

    public String getQuantity() {
        return quantity;
    }

    public int getId() {
        return id;
    }

    public void setTimeEstimate(int timeEstimate) {
        this.timeEstimate = timeEstimate;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setCookAssigned(User cookAssigned) {
        this.cookAssigned = cookAssigned;
    }

    public void setShiftAssigned(Shift shiftAssigned) {
        this.shiftAssigned = shiftAssigned;
    }

    public void setDetails(int timeEstimate,String quantity) {
        this.setQuantity(quantity);
        this.setTimeEstimate(timeEstimate);
    }

    public void setDetails(int timeEstimate) {
        this.setTimeEstimate(timeEstimate);
    }

    public void setDetails(String quantity) {
        this.quantity = quantity;
    }



    public static void saveAllNewTasks(SummarySheet summarySheet, Menu m) {
        String summarySheetInsert = "INSERT INTO catering.tasks (id_summarysheet, id_recipe, position) VALUES (?, ?, ?);";
        int[] result = PersistenceManager.executeBatchUpdate(summarySheetInsert, m.getAllRecipes().size(), new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, summarySheet.getId());
                ps.setInt(2, m.getAllRecipes().get(batchCount).getId());
                ps.setInt(3, batchCount);
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                summarySheet.getTasks().get(count).id = rs.getInt(1);
            }
        });
    }

    public static void saveNewTask(int summarySheetId, Task task, int index) {
        String taskInsert = "INSERT INTO tasks (id_summarysheet, position, id_recipe) " + "VALUES (" + summarySheetId + ", " + index + ", " + task.getKtAssigned().getId() + ");";
        PersistenceManager.executeUpdate(taskInsert);
        task.id = PersistenceManager.getLastId();
    }


    public static void resetTasks(SummarySheet summarySheet) {
        // DELETING FIRST
        deleteAllTasks(summarySheet);


        // SAVING ALL NEW TASKS
        saveAllNewTasks(summarySheet, summarySheet.getServiceAssigned().getMenu());
    }

    public static void deleteTask(Task task) {
        String query = "DELETE FROM tasks WHERE id = " + task.getId();
        PersistenceManager.executeUpdate(query);
    }

    public static void deleteAllTasks(SummarySheet summarySheet) {
        String query = "DELETE FROM tasks WHERE id_summarysheet = " + summarySheet.getId();
        PersistenceManager.executeUpdate(query);
    }

    public static void setTaskCompleted(Task task) {
        String query = "UPDATE tasks SET completed = 1 WHERE id = " + task.getId();
        PersistenceManager.executeUpdate(query);
    }

    public static void setCookChanged(Task task) {
        String query = "UPDATE tasks SET id_cook = " + (task.getCookAssigned() == null ? "null" : task.getCookAssigned().getId()) +
                " WHERE id = " + task.getId();
        PersistenceManager.executeUpdate(query);
    }

    public static void setShiftChanged(Task task) {
        String query = "UPDATE tasks SET id_shift = " + task.getShiftAssigned().getId() +
                " WHERE id = " + task.getId();
        PersistenceManager.executeUpdate(query);
    }

    public static void setTaskTimeChanged(Task task) {
        String query = "UPDATE tasks SET timeEstimate = " + task.getTimeEstimate() +
                " WHERE id = " + task.getId();
        PersistenceManager.executeUpdate(query);
    }

    public static void setTaskQuantityChanged(Task task) {
        String query = "UPDATE tasks SET quantity = " + task.getQuantity() +
                " WHERE id = " + task.getId();
        PersistenceManager.executeUpdate(query);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", timeEstimate=" + timeEstimate +
                ", quantity=" + quantity +
                ", completed=" + completed +
                ", shiftAssigned=" + shiftAssigned +
                ", cookAssigned=" + cookAssigned +
                ", ktAssigned=" + ktAssigned +
                '}';
    }

}
