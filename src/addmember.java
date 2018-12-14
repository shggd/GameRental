
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.awt.event.ActionEvent;

import model.Member;
import model.MemberTableModel;
import org.apache.commons.dbutils.*;


public class addmember extends JDialog {

    private final JPanel contentPanel;
    private JTextField textFieldID;
    private JTextField textField_FN;
    private JTextField textField_LN;

    //DB
    private String query;
    private Connection con;
    private PreparedStatement ps;


    //model
    private MemberTableModel model;

    /**
     * Launch the application.
     */


    /**
     * Create the dialog.
     */
    public addmember(MemberTableModel memberModel) {

        model = memberModel;
        this.setTitle("Add a member");
        this.setModal(true);


        contentPanel = new JPanel();
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            textFieldID = new JTextField();
            textFieldID.setEnabled(false);
            textFieldID.setEditable(false);
            textFieldID.setBounds(195, 38, 86, 20);
            contentPanel.add(textFieldID);
            textFieldID.setColumns(10);
        }
        {
            textField_FN = new JTextField();
            textField_FN.setBounds(195, 86, 86, 20);
            contentPanel.add(textField_FN);
            textField_FN.setColumns(15);
        }
        {
            textField_LN = new JTextField();
            textField_LN.setBounds(195, 134, 86, 20);
            contentPanel.add(textField_LN);
            textField_LN.setColumns(15);
        }
        {
            JLabel lblId = new JLabel("ID");
            lblId.setBounds(39, 41, 71, 14);
            contentPanel.add(lblId);
        }
        {
            JLabel lblFirstname = new JLabel("FIRST NAME");
            lblFirstname.setBounds(39, 88, 118, 17);
            contentPanel.add(lblFirstname);
        }
        {
            JLabel lblLastName = new JLabel("LAST NAME");
            lblLastName.setBounds(39, 137, 118, 14);
            contentPanel.add(lblLastName);
        }
        {
            JButton btnNewButton = new JButton("GENERATE ID");
            btnNewButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Random rnd = new Random();
                    int n = 100000 + rnd.nextInt(900000);
                    textFieldID.setText(Integer.toString(n));
                }
            });
            btnNewButton.setBounds(308, 37, 116, 23);
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
                        String firstname = textField_FN.getText();
                        String lastname = textField_LN.getText();
                        String memberid = textFieldID.getText();
                        if (firstname.isEmpty() || lastname.isEmpty() || memberid.isEmpty()) {
                            JOptionPane.showMessageDialog(contentPanel, "Something is Missing");
                        } else {
                            try {
                                //DB
                                con = database.connect();
                                query = "INSERT INTO members(memberid,firstname,lastname) VALUES (?, ?, ?)";
                                ps = con.prepareStatement(query);
                                ps.setString(1, memberid);
                                ps.setString(2, firstname);
                                ps.setString(3, lastname);
                                ps.execute();

                                //Success message
                                JOptionPane.showMessageDialog(contentPanel, "Success","Success",JOptionPane.INFORMATION_MESSAGE);

                                //update Model
                                model.addData(new Member(Integer.parseInt(memberid),firstname,lastname));
                                dispose();
                            } catch (SQLException e1) {
                                JOptionPane.showMessageDialog(contentPanel, e1);
                            }
                            finally {
                                DbUtils.closeQuietly(ps);
                                DbUtils.closeQuietly(con);
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

}
