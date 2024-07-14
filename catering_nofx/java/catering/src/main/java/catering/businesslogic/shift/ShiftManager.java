package catering.businesslogic.shift;

import java.util.ArrayList;

public class ShiftManager {



    public ShiftManager() {
        Shift.loadAllShifts();
    }

    public ArrayList<Shift> getShifts(){ return Shift.getAllShifts();}
}


