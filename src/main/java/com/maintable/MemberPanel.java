package com.maintable;

import com.database.Database;
import com.model.MemberTableModel;

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
import com.dialogs.AddMember;


public class MemberPanel {

    private JPanel panel;
    private JTable table;
    private JButton btnNewButton_3;
    private JButton btnNewButton_4;
    private JScrollPane scrollPane;
    private JTextField textField;
    private JLabel lblNewLabel;


    //DB property
    private String query = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    //Model
    private MemberTableModel model;


    public MemberPanel(MemberTableModel memberlist) {
        model = memberlist;
    }

    public void CreatePanel(){
        panel = new JPanel();
        panel.setLayout(null);

        table = new JTable(model);
        table.setDefaultEditor(Object.class, null);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Main Table
        scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 74, 592, 238);
        panel.add(scrollPane);
        scrollPane.setViewportView(table);


        //Add member button
        btnNewButton_3 = new JButton("Add Member");
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddMember add = new AddMember(model);
                add.setLocationRelativeTo(panel);
                add.setVisible(true);
            }
        });
        btnNewButton_3.setBounds(247, 27, 155, 23);
        panel.add(btnNewButton_3);


        //Delete member button
        btnNewButton_4 = new JButton("Delete him");
        btnNewButton_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(panel, "Select a row to delete", "Try again", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    try{
                        con = Database.connect();
                        String selected= table.getValueAt(row, 0).toString();
                        query ="delete from members where memberid = ?";
                        ps = con.prepareStatement(query);
                        ps.setString(1, selected);
                        int confirm = JOptionPane.showConfirmDialog(panel, "ARE YOU SURE???????", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (confirm == JOptionPane.YES_OPTION)
                        {
                            ps.execute();
                            JOptionPane.showMessageDialog(panel, "Success", "Success", JOptionPane.INFORMATION_MESSAGE);
                            model.removeRow(table.convertRowIndexToModel(table.getSelectedRow()));
                        }
                    }
                    catch(Exception e3)
                    {
                        JOptionPane.showMessageDialog(panel, e3);
                    }finally{
                        Database.close(con,ps,rs);
                    }

                }
            }
        });
        btnNewButton_4.setBounds(429, 27, 155, 23);
        panel.add(btnNewButton_4);

        //Search Bar
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);

        lblNewLabel = new JLabel("ID Search");
        lblNewLabel.setBounds(25, 30, 87, 14);
        panel.add(lblNewLabel);
        textField = new JTextField();
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e)
            {
                if (textField.getText().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(textField.getText(), 0,1));

                }
            }
        });
        textField.setBounds(85, 28, 114, 20);
        panel.add(textField);
        textField.setColumns(10);
    }

    public JPanel getPanel() {
        return panel;
    }
}
