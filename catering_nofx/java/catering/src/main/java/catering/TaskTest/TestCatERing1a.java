package catering.TaskTest;

import catering.businesslogic.CatERing;
import catering.businesslogic.TaskException;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchentask.SummarySheet;
import catering.businesslogic.menu.Menu;

public class TestCatERing1a {
    public static void main(String[] args) throws UseCaseLogicException {
        try {
            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());
            Menu.loadAllMenus();
            ServiceInfo service1 = ServiceInfo.loadServiceById(1);
            ServiceInfo service2 = ServiceInfo.loadServiceById(2);


            System.out.println("\nTEST GENERATING SUMMARY SHEET");
            SummarySheet sheet1 = CatERing.getInstance().getTaskManager().generateSummarySheet(service1);
            System.out.println("First sheet created: \n" + sheet1);

            System.out.println("\nTEST GENERATING SUMMARY SHEET");
            SummarySheet sheet2 = CatERing.getInstance().getTaskManager().generateSummarySheet(service2);
            System.out.println("Second sheet created: \n" + sheet2);

            System.out.println("\nTEST OPEN SUMMARY SHEET");
            System.out.println("Open first sheet: \n" + CatERing.getInstance().getTaskManager().openSummarySheet(sheet1));
            System.out.println("Open second sheet: \n" + CatERing.getInstance().getTaskManager().openSummarySheet(sheet2));
            // TEST: end
        } catch (TaskException e) {
            System.out.println("ERROR IN EXTENTION 1a");
            e.printStackTrace();
        }
    }
}
