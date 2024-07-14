package catering.businesslogic.kitchentask;

import catering.businesslogic.CatERing;
import catering.businesslogic.TaskException;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.recipe.KitchenTask;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;

import java.time.LocalDate;
import java.util.ArrayList;

public class TaskManager {

    private ArrayList<TaskEventReceiver> eventReceivers;

    private SummarySheet currentSummarySheet;

    public TaskManager(){
        eventReceivers = new ArrayList<>();
    }

    public void addEventReceiver(TaskEventReceiver ter) {
        this.eventReceivers.add(ter);
    }

    public void setCurrentSummarySheet(SummarySheet currentSumSheet) {
        this.currentSummarySheet = currentSumSheet;
    }


    public SummarySheet generateSummarySheet(ServiceInfo service) throws UseCaseLogicException,TaskException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if(!user.isChef()) {
            throw new UseCaseLogicException();
        }
        if(service.getMenu() == null) {
            throw new TaskException();
        }
        SummarySheet newSummarySheet = new SummarySheet(service, user);
        newSummarySheet.initSummarySheet();
        this.setCurrentSummarySheet(newSummarySheet);
        notifySummarySheetCreated(this.currentSummarySheet, service.getMenu());
        System.out.println(service.getMenu());

        return currentSummarySheet;
    }

    public SummarySheet openSummarySheet(SummarySheet summarySheet) throws UseCaseLogicException, TaskException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (!(user.isChef()))
            throw new UseCaseLogicException();
        if (!(summarySheet.isCreator(user)))
            throw new TaskException();
       this.setCurrentSummarySheet(summarySheet);
        return currentSummarySheet;
    }

    public void resetSummarySheet(SummarySheet summarySheet) throws UseCaseLogicException, TaskException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (!(user.isChef()))
            throw new UseCaseLogicException();
        if (!(summarySheet.isCreator(user)))
            throw new TaskException();
        summarySheet.resetSummarySheet();
        setCurrentSummarySheet(summarySheet);

        notifySummarySheettReset(summarySheet);
    }


    public Task addTask(KitchenTask kt) throws TaskException {
        if (currentSummarySheet == null)
            throw new TaskException();
        Task task = currentSummarySheet.addTask(kt);
        notifyTaskAdded(currentSummarySheet, task);
        return task;
    }

    public void deleteTask(Task task) throws TaskException {
        if (currentSummarySheet == null)
            throw new TaskException();
        currentSummarySheet.removeTask(task);
        notifyTaskDeleted(currentSummarySheet, task);
    }

    public void sortTask(Task task, int position ) throws UseCaseLogicException, TaskException {
        if(currentSummarySheet == null) {
            throw new UseCaseLogicException();
        }
        if(position < 0 || position > currentSummarySheet.getTasks().size()) {
            throw new TaskException();
        }
        currentSummarySheet.removeTask(task);
        currentSummarySheet.addTask(task, position);

        notifyTaskSorted(currentSummarySheet);

    }


    public void assignTask(Task toAssign, Shift shift, User cook) throws UseCaseLogicException, TaskException {
        if (currentSummarySheet == null || !(currentSummarySheet.containsTask(toAssign)))
            throw new UseCaseLogicException();
        if (cook != null && cook.isBusy())
            throw new TaskException();
        toAssign.assignTask(shift, cook);
        this.notifyTaskAssigned(toAssign);
    }

    public void assignTask(Task toAssign,Shift shift) throws TaskException, UseCaseLogicException {
        this.assignTask(toAssign, shift, null);
    }

    public void setTaskCompleted(Task task) throws UseCaseLogicException, TaskException {
        if (currentSummarySheet == null || !(currentSummarySheet.containsTask(task)))
            throw new UseCaseLogicException();
        task.setCompleted(true);
        notifyTaskCompleted(task);
    }

    public void editCookAssigned(Task toChange, User toAssign) throws UseCaseLogicException,TaskException {
        if (currentSummarySheet == null || !(currentSummarySheet.containsTask(toChange)))
            throw new UseCaseLogicException();

        if (toAssign != null && toAssign.isBusy())
            throw new TaskException();

        toChange.setCookAssigned(toAssign);
        notifyCookChanged(toChange);
    }

    public void editShiftAssigned(Task toChange, Shift toAssign) throws UseCaseLogicException {
        if (currentSummarySheet == null || !(currentSummarySheet.containsTask(toChange)))
            throw new UseCaseLogicException();

        toChange.setShiftAssigned(toAssign);

       notifyShiftChanged(toChange);
    }

    public void removeCookAssigned(Task toChange) throws UseCaseLogicException {
        if (currentSummarySheet == null || !(currentSummarySheet.containsTask(toChange)))
            throw new UseCaseLogicException();

        toChange.setCookAssigned(null);

        notifyCookRemoved(toChange);
    }

    public void setTaskDetails(Task toDetail, int timeEstimate,String quantity) throws UseCaseLogicException, TaskException {
        if (currentSummarySheet == null || !(currentSummarySheet.containsTask(toDetail)))
            throw new UseCaseLogicException();
        toDetail.setDetails(timeEstimate, quantity);
        notifyTaskDetailed(toDetail);
    }

    public void setTaskDetails(Task toDetail, int timeEstimate) throws TaskException, UseCaseLogicException {
        if (currentSummarySheet == null || !(currentSummarySheet.containsTask(toDetail)))
            throw new UseCaseLogicException();
        toDetail.setDetails(timeEstimate);
        notifyTaskDetailed(toDetail);
    }

    public void setTaskDetails(Task toDetail, String quantity) throws TaskException, UseCaseLogicException {
        if (currentSummarySheet == null || !(currentSummarySheet.containsTask(toDetail)))
            throw new UseCaseLogicException();
        toDetail.setDetails(quantity);
        notifyTaskDetailed(toDetail);
    }


    public void editTaskTimeEstimate(Task toChange, int timeEstimate) throws UseCaseLogicException {
        if (currentSummarySheet == null || !(currentSummarySheet.containsTask(toChange)))
            throw new UseCaseLogicException();

        toChange.setTimeEstimate(timeEstimate);

        notifyTaskTimeChanged(toChange);
    }

    public void editTaskQuantity(Task toChange, String quantity) throws UseCaseLogicException {
        if (currentSummarySheet == null || !(currentSummarySheet.containsTask(toChange)))
            throw new UseCaseLogicException();

        toChange.setQuantity(quantity);

        notifyTaskQuantityChanged(toChange);
    }

    private void notifySummarySheetCreated(SummarySheet currentSheet, Menu menu) {
        for (TaskEventReceiver er : eventReceivers) {
            er.updateSummarySheetCreated(currentSheet, menu);
        }
    }

    private void notifySummarySheettReset(SummarySheet summarySheet) {
        for (TaskEventReceiver er : eventReceivers) {
            er.updateSummarySheetReset(summarySheet);
        }
    }

    private void notifyTaskAssigned (Task task){
        for (TaskEventReceiver er : eventReceivers) {
            er.updateTaskAssigned(task);
        }
    }

    private void  notifyTaskCompleted(Task task) {
        for (TaskEventReceiver er : eventReceivers) {
            er.updateTaskCompleted(task);
        }
    }

    private void notifyCookChanged(Task task) {
        for (TaskEventReceiver er : eventReceivers) {
            er.updateCookChanged(task);
        }
    }

    private void notifyCookRemoved(Task task) {
        for (TaskEventReceiver er : eventReceivers) {
            er.updateCookRemoved(task);
        }
    }

    private void notifyShiftChanged(Task task) {
        for (TaskEventReceiver er : eventReceivers) {
            er.updateShiftChanged(task);
        }
    }

    private void notifyTaskAdded (SummarySheet currentSummarySheet, Task task){
        for (TaskEventReceiver er : eventReceivers) {
            er.updateTaskAdded(currentSummarySheet, task);
        }
    }

    private void notifyTaskDeleted(SummarySheet summarySheet, Task task) {
        for (TaskEventReceiver er : eventReceivers) {
            er.updateTaskDeleted(task);
        }
    }

    private void notifyTaskSorted (SummarySheet currentSummarySheet){
        for (TaskEventReceiver er : eventReceivers) {
            er.updateTaskSorted(currentSummarySheet);
        }
    }

    private void notifyTaskDetailed (Task task){
        for (TaskEventReceiver er : eventReceivers) {
            er.updateTaskDetailed(task);
        }
    }

    private void notifyTaskTimeChanged(Task task) {
        for (TaskEventReceiver er : eventReceivers) {
            er.updateTaskTimeChanged(task);
        }
    }

    private void notifyTaskQuantityChanged(Task task) {
        for(TaskEventReceiver er : eventReceivers) {
            er.updateTaskQuantityChanged(task);
        }
    }
}
