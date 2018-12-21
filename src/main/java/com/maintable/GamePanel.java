package com.maintable;

import com.database.Database;
import com.model.GameTableModel;

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
import com.dialogs.AddGame;

public class GamePanel {

    private JPanel panel;
    private JTable table;
    private JTextField textField;
    private JButton btnNewButton;
    private JButton btnNewButton_1;
    private JButton btnNewButton_2;
    private JScrollPane scrollPane;

    //DB property
    private String query = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    //Model
    private GameTableModel model;

    public GamePanel(GameTableModel gamelist) {
        model = gamelist;
    }

    //Game Panel
    public void CreatePanel() {
        panel = new JPanel();
        panel.setLayout(null);

        table = new JTable(model);
        table.setDefaultEditor(Object.class, null);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 34, 360, 286);
        panel.add(scrollPane);
        scrollPane.setViewportView(table);


        btnNewButton_1 = new JButton("Add Game");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JDialog add = new AddGame(model);
                add.setLocationRelativeTo(panel);
                add.setVisible(true);
            }
        });

        btnNewButton_1.setBounds(418, 56, 125, 23);
        panel.add(btnNewButton_1);

        btnNewButton_2 = new JButton("Delete Game");
        btnNewButton_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(panel, "Select a row to delete", "Try again", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    try {
                        con = Database.connect();
                        String selected = table.getValueAt(row, 0).toString();
                        query = "select gameid from borrow where gameid = ?";
                        ps = con.prepareStatement(query);
                        ps.setString(1, selected);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            JOptionPane.showMessageDialog(panel, "Game is being borrowed", "Game is Out", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            int comfirm = JOptionPane.showConfirmDialog(panel, "ARE YOU SURE???????", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (comfirm == JOptionPane.YES_OPTION) {
                                query = "delete from game where gameid = ?";
                                ps = con.prepareStatement(query);
                                ps.setString(1, selected);
                                ps.execute();
                                JOptionPane.showMessageDialog(panel, "Success", "Success", JOptionPane.INFORMATION_MESSAGE);
                                model.removeRow(table.convertRowIndexToModel(table.getSelectedRow()));
                            }
                        }
                    } catch (SQLException e3) {
                        JOptionPane.showMessageDialog(null, e3);
                    } finally {
                        Database.close(con, ps, rs);
                    }

                }

            }
        });
        btnNewButton_2.setBounds(418, 98, 125, 23);
        panel.add(btnNewButton_2);

        //Search Bar
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);
        textField = new JTextField(20);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (textField.getText().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(textField.getText(), 0,1,2));

                }
            }
        });

        textField.setBounds(102, 12, 165, 20);
        panel.add(textField);

        JLabel lblSearchBy = new JLabel("ID Search");
        lblSearchBy.setBounds(41, 14, 125, 17);
        lblSearchBy.setLabelFor(textField);
        panel.add(lblSearchBy);

        btnNewButton = new JButton("Refresh Table");
        btnNewButton.setBounds(86, 331, 181, 23);
        panel.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                table.repaint();
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
