package model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class RentalTableModel  extends AbstractTableModel {

    private final String[] columns = {"Game ID","Member ID","Due Date","Rental Date", " Return Date"};
    private List<Rental> rentalList;

    public RentalTableModel(List<Rental> rentalList) {
        this.rentalList = rentalList;
    }

    public void addData(Rental r){
        rentalList.add(r);
        fireTableRowsInserted(rentalList.size()-1,rentalList.size()-1);
    }

    public void removeRow(int row){
        rentalList.remove(row);
        fireTableRowsDeleted(row,row);
    }


    @Override
    public int getRowCount() {
        return rentalList.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Rental r = rentalList.get(row);
        switch(col){
            case 0:return r.getGameID();
            case 1:return r.getMemberID();
            case 2:return r.getDueDate();
            case 3:return r.getRentDate();
            case 4:return r.getReturnDate();
            default:return null;
        }
    }
}
