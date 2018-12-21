package com.model;

import javafx.print.Collation;

import javax.swing.table.AbstractTableModel;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GameTableModel extends AbstractTableModel {

    private final String[] columns = {"ID","Title","Publisher","Avaliable"};
    private List<Game> gameList;

    public GameTableModel(List<Game> gameList) {
        this.gameList = gameList;
    }

    public void addData(Game g){
        gameList.add(g);
        fireTableRowsInserted(gameList.size()-1,gameList.size()-1);
    }

    public void removeRow(int row){
        gameList.remove(row);
        fireTableRowsDeleted(row,row);
    }
    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public int getRowCount(){
        return gameList.size();
    }

    @Override
    public Object getValueAt(int row, int col){
        Game g = gameList.get(row);
        switch(col){
            case 0:return g.getId();
            case 1:return g.getTitle();
            case 2:return g.getPublisher();
            case 3:return g.getAvailable();
            default:return null;
        }
    }
    public void updateValue(int gameid,String available){
        Comparator<Game> c = new Comparator<Game>() {
            @Override
            public int compare(Game o1, Game o2) {
                if(o1.getId()<o2.getId()){
                    return -1;
                }
                if(o1.getId()>o2.getId()){
                    return 1;
                }
                return 0;
            }
        };

        int index = Collections.binarySearch(gameList,new Game(gameid,null,null,null),c);
        if(index>=0){
            gameList.get(index).setAvailable(available);
            fireTableDataChanged();
        }
    }



    public String getColumnName(int column) {
        return columns[column];
    }
}
