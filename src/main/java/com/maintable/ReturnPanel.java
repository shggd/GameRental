package com.maintable;

import com.database.Database;
import com.model.GameTableModel;
import com.model.RentalTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReturnPanel {

    private JPanel panel;
    private JScrollPane scrollPane;
    private JTable table;
    private JButton btnNewButton;
    private JTextField textField;
    private JLabel searchLabel;

    //DB
    private String query = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;


    private RentalTableModel model;
    private GameTableModel gameModel;

    public ReturnPanel(RentalTableModel model, GameTableModel gameModel) {
        this.model = model;
        this.gameModel =gameModel;
    }

    public void CreatePanel(){
        panel = new JPanel();
        panel.setLayout(null);

        //Main table
        scrollPane = new JScrollPane();
        scrollPane.setBounds(40, 51, 538, 161);
        panel.add(scrollPane);
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(table);

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);


        //Return Button
        btnNewButton = new JButton("RETURN");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if(row==-1){
                    JOptionPane.showMessageDialog(panel, "Select an item to Return", "Try again", JOptionPane.INFORMATION_MESSAGE);
                }else {
                    try {
                        con = Database.connect();
                        //delete entry from rental table
                        String selected = table.getValueAt(row, 0).toString();
                        query = "delete from borrow where gameid = ?";
                        ps = con.prepareStatement(query);
                        ps.setString(1, selected);
                        ps.execute();

                        //set the game available
                        query = "update game set available = 'yes' where gameid = ?";
                        ps = con.prepareStatement(query);
                        ps.setString(1, selected);
                        ps.execute();
                        JOptionPane.showMessageDialog(panel, "Item Returned");

                        //remove item from model
                        model.removeRow(table.convertRowIndexToModel(table.getSelectedRow()));
                        //update game model
                        gameModel.updateValue(Integer.parseInt(selected),"yes");
                    } catch (SQLException er) {
                        JOptionPane.showMessageDialog(panel, er);
                    } finally {
                        Database.close(con, ps, rs);
                    }
                }
            }
        });
        btnNewButton.setBounds(220, 239, 125, 23);
        panel.add(btnNewButton);





        searchLabel = new JLabel("Search");
        searchLabel.setBounds(180,15,165,20);
        panel.add(searchLabel);
        textField = new JTextField(15);
        textField.setBounds(230, 15, 165, 20);
        panel.add(textField);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (textField.getText().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(textField.getText(), 0,1));

                }
            }
        });

    }


    public JPanel getPanel(){
        return panel;
    }
}
