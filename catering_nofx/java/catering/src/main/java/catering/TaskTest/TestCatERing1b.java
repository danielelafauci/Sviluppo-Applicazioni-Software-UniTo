package catering.TaskTest;

import catering.businesslogic.CatERing;
import catering.businesslogic.TaskException;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchentask.SummarySheet;
import catering.businesslogic.kitchentask.Task;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;

import java.util.ArrayList;
import java.util.List;

public class TestCatERing1b {
    public static void main(String[] args) throws TaskException, UseCaseLogicException {
        try {


            System.out.println("[TEST]: FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

            // LOADING DUMMY DATA
            Menu.loadAllMenus();
            ServiceInfo service1 = ServiceInfo.loadServiceById(1);
            ServiceInfo service2 = ServiceInfo.loadServiceById(2);
            List<Shift> shifts = CatERing.getInstance().getShiftManager().getShifts();

            // TEST: start
            System.out.println("\n[TEST]: GENERATING SUMMARY SHEET");
            SummarySheet sheet1 = CatERing.getInstance().getTaskManager().generateSummarySheet(service1);
            System.out.println("First sheet created: \n" + sheet1);

            System.out.println("\n[TEST]: TASK ASSIGNMENT (WITH COOK)");
            CatERing.getInstance().getTaskManager().assignTask(sheet1.getTasks().get(0), shifts.get(0), User.loadUserById(5));

            System.out.println(sheet1);

            System.out.println("\n[TEST]: TASK INFO DEFINITION");
            CatERing.getInstance().getTaskManager().setTaskDetails(sheet1.getTasks().get(0), 90, "2");

            System.out.println(sheet1);

            System.out.println("\n[TEST]: RESET SHEET");
            CatERing.getInstance().getTaskManager().resetSummarySheet(sheet1);

            System.out.println("First sheet reset: \n" + sheet1);
            // TEST: end
        } catch (UseCaseLogicException | TaskException e) {
            System.out.println("Errore su estensione 1b");
            e.printStackTrace();
        }
    }
}
