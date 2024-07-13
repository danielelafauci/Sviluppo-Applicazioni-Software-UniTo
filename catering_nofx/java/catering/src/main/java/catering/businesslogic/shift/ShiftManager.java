package catering.businesslogic.shift;

import java.util.ArrayList;

public class ShiftManager {

    private WorkShiftBoard wb;

    public ShiftManager() {
        Shift.loadAllShifts();
    }

    public ArrayList<Shift> getShifts(){ return Shift.getAllShifts();}
}


