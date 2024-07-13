package catering.TaskTest;

import catering.businesslogic.CatERing;
import catering.businesslogic.TaskException;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchentask.SummarySheet;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.shift.Shift;

import java.util.List;

public class TestCatERing2 {
    public static void main(String[] args) throws TaskException, UseCaseLogicException {
        System.out.println("TEST FAKE LOGIN");
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());
        Menu.loadAllMenus();
        ServiceInfo service1 = ServiceInfo.loadServiceById(1);
        List<Shift> shifts = CatERing.getInstance().getShiftManager().getShifts();
        System.out.println("\nTEST GENERATING SUMMARY SHEET");
        SummarySheet sheet1 = CatERing.getInstance().getTaskManager().generateSummarySheet(service1);
        System.out.println(sheet1);



    }
}
