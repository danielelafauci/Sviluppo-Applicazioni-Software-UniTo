package catering.businesslogic.shift;

import java.util.ArrayList;
import java.util.Map;

public class WorkShiftBoard {

    private Map<Integer, Shift> shifts;

    public void loadWorkshiftBoard() {
        shifts = Shift.loadAllShifts();
    }

    public ArrayList<Shift> getShifts() {
        loadWorkshiftBoard();
        return new ArrayList<>(shifts.values());
    }
}
