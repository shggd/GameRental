
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Random;

import model.Game;
import model.GameTableModel;


public class addgame extends JDialog {

    private final JPanel contentPanel;
    ButtonGroup bg;
    private JTextField textField_PUBLISHER;
    private JTextField textField_TITLE;
    private JTextField textField_GAMEID;

    //model
    private GameTableModel model;

    //Db
    private String query = null;
    private Connection con = null;
    private PreparedStatement ps;

    /**
     * Create the dialog.
     */
    public addgame(GameTableModel gamemodel) {

        model = gamemodel;
        this.setTitle("Add a Game");
        this.setModal(true);

        contentPanel = new JPanel();
        setBounds(100, 100, 450, 300);
        bg = new ButtonGroup();
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            JLabel lblNewLabel = new JLabel("GAME ID");
            lblNewLabel.setBounds(38, 62, 86, 14);
            contentPanel.add(lblNewLabel);
        }
        {
            JLabel lblNewLabel_1 = new JLabel("TITLE");
            lblNewLabel_1.setBounds(38, 108, 86, 14);
            contentPanel.add(lblNewLabel_1);
        }
        {
            JLabel lblNewLabel_2 = new JLabel("PUBLISHER");
            lblNewLabel_2.setBounds(38, 151, 86, 14);
            contentPanel.add(lblNewLabel_2);
        }
        {
            textField_PUBLISHER = new JTextField(8);
            textField_PUBLISHER.setBounds(194, 148, 86, 20);
            contentPanel.add(textField_PUBLISHER);
            textField_PUBLISHER.setColumns(10);
        }
        {
            textField_TITLE = new JTextField(20);
            textField_TITLE.setBounds(194, 105, 86, 20);
            contentPanel.add(textField_TITLE);
            textField_TITLE.setColumns(10);
        }
        {
            textField_GAMEID = new JTextField(20);
            textField_GAMEID.setEnabled(false);
            textField_GAMEID.setEditable(false);
            textField_GAMEID.setBounds(194, 59, 86, 20);
            contentPanel.add(textField_GAMEID);
            textField_GAMEID.setColumns(10);
        }
        {
            JLabel lblAvailable = new JLabel("AVAILABLE");
            lblAvailable.setBounds(38, 192, 95, 14);
            contentPanel.add(lblAvailable);
        }
        {
            JRadioButton rdbtnYes = new JRadioButton("yes");
            rdbtnYes.setBounds(194, 188, 67, 23);
            contentPanel.add(rdbtnYes);
            bg.add(rdbtnYes);
        }
        {
            JRadioButton rdbtnNo = new JRadioButton("no");
            rdbtnNo.setBounds(260, 188, 109, 23);
            contentPanel.add(rdbtnNo);
            bg.add(rdbtnNo);
        }
        {
            JButton btnNewButton = new JButton("ID");
            btnNewButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    Random rnd = new Random();
                    int n = 10000000 + rnd.nextInt(90000000);
                    textField_GAMEID.setText(Integer.toString(n));
                }
            });
            btnNewButton.setBounds(323, 58, 89, 23);
            contentPanel.add(btnNewButton);
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String id = textField_GAMEID.getText();
                        String title = textField_TITLE.getText();
                        String publisher = textField_PUBLISHER.getText();
                        String available = getSelectedButtonText(bg);

                        if (id.isEmpty() || title.isEmpty() || publisher.isEmpty() || available== null) {
                            //ERROR DISPLAY
                            JOptionPane.showMessageDialog(contentPanel, "Something is Missing");
                        }else{
                            try {
                                //DB
                                con = database.connect();
                                query = "INSERT INTO game(gameid,title,publisher,available) VALUES (?, ?, ?, ?)";
                                ps = con.prepareStatement(query);
                                ps.setString(1, id);
                                ps.setString(2, title);
                                ps.setString(3, publisher);
                                ps.setString(4, available);
                                ps.execute();

                                //Success message
                                JOptionPane.showMessageDialog(contentPanel, "Success","Success",JOptionPane.INFORMATION_MESSAGE);

                                //update Model
                                model.addData(new Game(Integer.parseInt(id),title,publisher,available));
                                dispose();
                            } catch (SQLException e1) {
                                JOptionPane.showMessageDialog(contentPanel, e1);

                            } finally {
                                database.close(con,ps,null);

                            }
                        }
                    }
                });
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
    }
    /**
     * Launch the application.
     */

    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }
}
