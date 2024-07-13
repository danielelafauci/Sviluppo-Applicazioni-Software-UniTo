package catering.persistence;

import catering.businesslogic.kitchentask.SummarySheet;
import catering.businesslogic.kitchentask.Task;
import catering.businesslogic.kitchentask.TaskEventReceiver;
import catering.businesslogic.menu.Menu;

public class TaskPersistence implements TaskEventReceiver {

    public void updateSummarySheetCreated(SummarySheet summarySheet, Menu menu) {SummarySheet.saveSummarySheet(summarySheet,menu);}

    public void updateSummarySheetReset(SummarySheet summarySheet) {Task.resetTasks(summarySheet);}

    public void updateTaskAdded(SummarySheet summarySheet, Task task){Task.saveNewTask(summarySheet.getId(), task, summarySheet.getTaskIndex(task));}

    public void updateTaskDeleted(Task task) {Task.deleteTask(task);
    }

    public void updateTaskSorted(SummarySheet sheet) {
        SummarySheet.sortTasks(sheet);
    }

    public void updateTaskAssigned(Task task) {SummarySheet.setAssignment(task);}

    public void updateTaskCompleted(Task task) {Task.setTaskCompleted(task);}

    public void updateCookChanged(Task task) {Task.setCookChanged(task);}

    public void updateCookRemoved(Task task){Task.setCookChanged(task);}

    public void updateShiftChanged(Task task) {Task.setShiftChanged(task);}

    public void updateTaskDetailed(Task task) {SummarySheet.setTaskDetails(task);}

    public void updateTaskTimeChanged(Task task){Task.setTaskTimeChanged(task);}

    public void updateTaskQuantityChanged(Task task){Task.setTaskQuantityChanged(task);}



}