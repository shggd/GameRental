import model.GameTableModel;
import model.MemberTableModel;
import model.Rental;
import model.RentalTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.time.LocalDate;

public class rentalpanel {

    private JPanel panel;
    private JScrollPane scrollPane;
    private JScrollPane scrollPane_2;
    private JTable table;
    private JTable table_2;
    private JLabel lblIdSearch_1;
    private JLabel lblIdSearch_2;
    private JTextField textField;
    private JTextField textField_2;
    private JButton btnBorrow;

    //DB property
    private String query = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    //Model
    private MemberTableModel memberModel;
    private GameTableModel gameModel;
    private RentalTableModel rentModel;

    public rentalpanel(MemberTableModel memberModel, GameTableModel gameModel, RentalTableModel rentModel) {
        this.memberModel = memberModel;
        this.gameModel = gameModel;
        this.rentModel = rentModel;
    }

    public void CreatePanel(){

        panel = new JPanel();
        panel.setLayout(null);

        //Game TABLE
        scrollPane = new JScrollPane();
        scrollPane.setBounds(270, 51, 332, 200);
        panel.add(scrollPane);
        table = new JTable(gameModel);
        scrollPane.setViewportView(table);
        table.setDefaultEditor(Object.class, null);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableRowSorter<TableModel> gameSorter = new TableRowSorter<TableModel>(gameModel);
        table.setRowSorter(gameSorter);

        //Member TABLE
        scrollPane_2 = new JScrollPane();
            scrollPane_2.setBounds(10, 51, 220, 200);
        panel.add(scrollPane_2);
        table_2 = new JTable(memberModel);
        scrollPane_2.setViewportView(table_2);
        table_2.setDefaultEditor(Object.class, null);
        table_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //member Search
        TableRowSorter<TableModel> memberSorter = new TableRowSorter<TableModel>(memberModel);
        table_2.setRowSorter(memberSorter);
        gameSorter.setRowFilter(RowFilter.regexFilter("yes",3));

        lblIdSearch_1 = new JLabel("Member Search");
        lblIdSearch_1.setBounds(10, 23, 66, 14);
        panel.add(lblIdSearch_1);

        textField = new JTextField(15);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e)
            {
                if (textField.getText().length() == 0) {
                    memberSorter.setRowFilter(null);
                } else {
                    memberSorter.setRowFilter(RowFilter.regexFilter(textField.getText(), 0,1,2));
                }
            }
        });
        textField.setBounds(86, 20, 86, 20);
        panel.add(textField);

//        Game Search
        lblIdSearch_2 = new JLabel("Search");
        lblIdSearch_2.setBounds(350, 20, 100, 23);
        panel.add(lblIdSearch_2);
        textField_2 = new JTextField(15);
        textField_2.setBounds(400,23,86,20);
        panel.add(textField_2);
        textField_2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (textField_2.getText().length() == 0) {
                    gameSorter.setRowFilter(RowFilter.regexFilter("yes",3));
                } else {
                    gameSorter.setRowFilter(RowFilter.regexFilter(textField_2.getText(), 0,1,2));

                }
            }

        });

        //Rent
        btnBorrow = new JButton("Rent");
        btnBorrow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                int row1 = table_2.getSelectedRow();
                if(row==-1||row1==-1){
                    JOptionPane.showMessageDialog(panel, "Please select a game and a member");
                }
                else {
                    try {
                        String selected1 = table_2.getValueAt(row1, 0).toString();
                        String selected = table.getValueAt(row, 0).toString();
                        LocalDate date = LocalDate.now();
                        date = date.plusDays(30);
                        Date d = Date.valueOf(date);

                        //Add to Borrow
                        con = database.connect();
                        query = "INSERT INTO borrow(gameid,memberid,due_date,borrow_date,return_date) VALUES (?,?,?,?,?)";
                        ps = con.prepareStatement(query);
                        ps.setString(1, selected);
                        ps.setString(2, selected1);
                        ps.setDate(3, d);
                        ps.setDate(4, getCurrentDate());
                        ps.setNull(5, java.sql.Types.DATE);
                        ps.execute();

                        //Set game to unavailable
                        query = "update game set available = 'no' where gameid = ?";
                        ps = con.prepareStatement(query);
                        ps.setString(1, selected);
                        ps.execute();
                        JOptionPane.showMessageDialog(panel, "Success, Please return item within 30 Day");

                        //Add to model
                        rentModel.addData(new Rental(Integer.parseInt(selected),Integer.parseInt(selected1),d,getCurrentDate(),null));

                        gameModel.updateValue(Integer.parseInt(selected),"no");
                    } catch (Exception a) {
                        JOptionPane.showMessageDialog(panel, a);
                    } finally {

                    }
                }
            }
        });
        btnBorrow.setBounds(202, 300, 122, 23);
        panel.add(btnBorrow);
    }

    private static java.sql.Date getCurrentDate() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }

    public JPanel getPanel(){
        return panel;
    }


}
