package catering.TaskTest;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchentask.SummarySheet;
import catering.businesslogic.menu.Menu;

public class TestCatERing5b {
    public static void main(String[] args) throws UseCaseLogicException {
        try {
            System.out.println("[TEST]: FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

            // LOADING DUMMY DATA
            Menu.loadAllMenus();
            ServiceInfo service1 = ServiceInfo.loadServiceById(1);

            // TEST: start
            System.out.println("\n[TEST]: GENERATING SUMMARY SHEET");
            SummarySheet sheet1 = CatERing.getInstance().getTaskManager().generateSummarySheet(service1);

            System.out.println(sheet1);

            System.out.println("\n[TEST]: SET TASK COMPLETED");
            CatERing.getInstance().getTaskManager().setTaskCompleted(sheet1.getTasks().get(0));

            System.out.println(sheet1);
            // TEST: end

        } catch (Exception e) {
            System.out.println("Errore su estensione 5a");
            e.printStackTrace();
        }
    }
}

