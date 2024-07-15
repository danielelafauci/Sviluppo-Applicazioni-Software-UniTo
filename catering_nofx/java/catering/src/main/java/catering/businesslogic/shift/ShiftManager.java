package catering.businesslogic.shift;

import java.util.ArrayList;

public class ShiftManager {

    private WorkShiftBoard workShiftBoard;

    public ShiftManager() {
        this.workShiftBoard = new WorkShiftBoard();
    }

    public ArrayList<Shift> getWorkshiftBoard(){ return workShiftBoard.getShifts();}
}


