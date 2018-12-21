package com.login;

import org.apache.commons.dbutils.DbUtils;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.database.Database;
import com.maintable.*;

public class LoginPage {

    private JFrame frame;
    private JTextField textField;
    private JPasswordField passwordField;

    private String query = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;


    public LoginPage() {
        frame = new JFrame();
        frame.setTitle("APP");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblUser = new JLabel("User");
        lblUser.setBounds(45, 51, 97, 14);
        frame.getContentPane().add(lblUser);
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(45, 112, 97, 14);
        frame.getContentPane().add(lblPassword);


        textField = new JTextField();
        textField.setBounds(204, 48, 86, 20);
        frame.getContentPane().add(textField);
        textField.setColumns(10);
        passwordField = new JPasswordField();
        passwordField.setBounds(204, 109, 86, 20);
        frame.getContentPane().add(passwordField);

        JButton btnLogin = new JButton("LOGIN");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    String username  = textField.getText();
                    String password = String.valueOf(passwordField.getPassword());

                    con = Database.connect();
                    query = "select * from logs where login =? and pass =?";
                    ps = con.prepareStatement(query);
                    ps.setString(1, username);
                    ps.setString(2, password);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(frame, "Success");
                        frame.dispose();
                        table hi = new table();
                        hi.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Wrong username or password");
                    }
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, e1);
                } finally {
                    DbUtils.closeQuietly(rs);
                    DbUtils.closeQuietly(ps);
                    DbUtils.closeQuietly(con);
                }
            }

        });
        btnLogin.setBounds(80, 170, 89, 23);
        frame.getContentPane().add(btnLogin);

        JButton btnNewButton = new JButton("Register");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Register reg = new Register();
                reg.setLocationRelativeTo(frame);
                reg.setVisible(true);
            }
        });
        btnNewButton.setBounds(201, 170, 89, 23);
        frame.getContentPane().add(btnNewButton);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
