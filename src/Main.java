import model.*;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class Main {

    public List test(){

        List GameList = new ArrayList<Game>();
        try{
            Connection x = database.connect();
            String query = "select * from game ORDER BY gameid";
            PreparedStatement pp = x.prepareStatement(query);
            ResultSet rs = pp.executeQuery();
            while(rs.next()){
                GameList.add(new Game(Integer.parseInt(rs.getString(1)),rs.getString(2),rs.getString(3),rs.getString(4)));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return GameList;
    }

    public static void main(String[] args) {


        List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>(2);
        Main x = new Main();
        List l = x.test();

        JFrame frame = new JFrame("Cool");
        JTextField tf = new JTextField(15);
        JLabel lblFName = new JLabel("First Name:");

        TableModel model = new GameTableModel(l);
        JTable table = new JTable(model);

        frame.setLayout(new FlowLayout());
        lblFName.setLabelFor(tf);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(lblFName);
        frame.add(tf);
        frame.add(new JScrollPane(table));

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);
        filters.add(RowFilter.regexFilter("no",3));
        RowFilter<Object,Object> r1 = RowFilter.andFilter(filters);
//        sorter.setRowFilter(r1);

        JButton deleting = new JButton("DELETE");
        frame.add(deleting);

        deleting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                ((GameTableModel) model).addData(new Game(213,"DE","CVD","YAA"));
//                System.out.println(table.convertRowIndexToModel(table.getSelectedRow()));
//                ((GameTableModel) model).removeRow(table.convertRowIndexToModel(table.getSelectedRow()));
                ((GameTableModel) model).updateValue(59709452,"no");
            }
        });

        tf.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                // on each key type event filter.
                // put your filter code as submit button
                String text = tf.getText();
//                RowFilter<TableModel, Object> rf = null;
//                rf = rf.regexFilter("C",2);
                if (text.length() == 0) {
                    RowFilter<Object,Object> fooBarFilter = RowFilter.andFilter(filters);
                    sorter.setRowFilter(RowFilter.regexFilter("no",3));
                }
                else{
                    filters.add(RowFilter.regexFilter(text,0));
                    RowFilter<Object,Object> r2 = RowFilter.andFilter(filters);
                    sorter.setRowFilter(r2);

                }
            }
        });


        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
