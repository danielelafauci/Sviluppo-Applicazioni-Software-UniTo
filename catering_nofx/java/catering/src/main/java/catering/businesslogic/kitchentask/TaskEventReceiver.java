package catering.businesslogic.kitchentask;

import catering.businesslogic.menu.Menu;

public interface TaskEventReceiver {
    void updateSummarySheetCreated(SummarySheet summarySheet, Menu menu);

    void updateSummarySheetReset(SummarySheet summarySheet);

    void updateTaskAdded(SummarySheet summarySheet, Task task);

    void updateTaskDeleted(Task task);

    void updateTaskSorted(SummarySheet summarySheet);

    void updateTaskAssigned(Task task);

    void updateTaskCompleted(Task task);

    void updateCookChanged(Task task);

    void updateCookRemoved(Task task);

    void updateTaskDetailed(Task task);

    void updateShiftChanged(Task task);

    void updateTaskTimeChanged(Task task);

    void updateTaskQuantityChanged(Task task);
}
