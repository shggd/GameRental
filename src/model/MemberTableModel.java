package model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class MemberTableModel extends AbstractTableModel {

    private final String[] columns = {"Member Id","First Name","Last Name"};
    private List<Member> memberList;

    public MemberTableModel(List<Member> memberList) {
        this.memberList = memberList;
    }

    public void addData(Member m){
        memberList.add(m);
        fireTableRowsInserted(memberList.size()-1,memberList.size()-1);
    }

    public void removeRow(int row){
        memberList.remove(row);
        fireTableRowsDeleted(row,row);
    }

    @Override
    public int getRowCount() {
        return memberList.size();
    }

    @Override
    public int getColumnCount() {
        return  columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Member m = memberList.get(row);
        switch(col){
            case 0:return m.getId();
            case 1:return m.getFirstName();
            case 2:return m.getLastName();
            default:return null;
        }
    }
}
