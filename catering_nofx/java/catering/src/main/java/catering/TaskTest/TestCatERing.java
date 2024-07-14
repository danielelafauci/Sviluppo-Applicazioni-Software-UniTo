package catering.TaskTest;

import catering.businesslogic.CatERing;
import catering.businesslogic.TaskException;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchentask.SummarySheet;
import catering.businesslogic.kitchentask.Task;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.recipe.KitchenTask;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;

import org.w3c.dom.events.Event;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class TestCatERing {
    public static void main(String[] args) throws TaskException, UseCaseLogicException {

        System.out.println("[TEST]: FAKE LOGIN");
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

        // LOADING DUMMY DATA
        Menu.loadAllMenus();
        ServiceInfo service = ServiceInfo.loadServiceById(1);

        // TEST: Starting
        System.out.println("\n[TEST]: GENERATING SUMMARY SHEET");
        SummarySheet sheet = CatERing.getInstance().getTaskManager().generateSummarySheet(service);

        System.out.println(sheet);

        System.out.println("\n[TEST]: ADD NEW TASK");
        KitchenTask kitchenTask = new KitchenTask();
        kitchenTask.setAuthor(CatERing.getInstance().getUserManager().getCurrentUser());
        kitchenTask.setOwner(CatERing.getInstance().getUserManager().getCurrentUser());
        kitchenTask.setName("LASAGNE ");
        kitchenTask.setDescription("TEST");
        kitchenTask.setPublished(true);;
        System.out.println("NEW KITCHEN TASK " + kitchenTask);
        CatERing.getInstance().getTaskManager().addTask(kitchenTask);
        System.out.println(sheet);


        System.out.println("\n[TEST]: REORDERING SUMMARY SHEET's TASKS");
        CatERing.getInstance().getTaskManager().sortTask(sheet.getTasks().get(0), 3);

        System.out.println(sheet);

        System.out.println("\n[TEST]: GET WORKSHIFTBOARD");
        List<Shift> shifts = CatERing.getInstance().getShiftManager().getShifts();
        System.out.println(shifts);

        System.out.println("\n[TEST]: TASK ASSIGNMENT (WITH COOK), FIRST TASK");
        CatERing.getInstance().getTaskManager().assignTask(sheet.getTasks().get(0), shifts.get(0), User.loadUserById(5));
        System.out.println(sheet);

        System.out.println("\n[TEST]: TASK ASSIGNMENT (WITH COOK), SECOND TASK");
        CatERing.getInstance().getTaskManager().assignTask(sheet.getTasks().get(1), shifts.get(0), User.loadUserById(5));

        System.out.println(sheet);

        System.out.println("\n[TEST]: TASK ASSIGNMENT (NO COOK), THIRD TASK");
        CatERing.getInstance().getTaskManager().assignTask(sheet.getTasks().get(2), shifts.get(0));

        System.out.println(sheet);

        System.out.println("\n[TEST]: TASK INFO DEFINITION");

        CatERing.getInstance().getTaskManager().setTaskDetails(sheet.getTasks().get(0),90);
        System.out.println(sheet);
        CatERing.getInstance().getTaskManager().setTaskDetails(sheet.getTasks().get(0),"2");
        System.out.println(sheet);
        CatERing.getInstance().getTaskManager().setTaskDetails(sheet.getTasks().get(0), 100, "5");
        System.out.println(sheet);
    }
}
