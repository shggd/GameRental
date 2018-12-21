package com.login;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import com.database.Database;
import org.apache.commons.dbutils.DbUtils;


public class Register extends JDialog {

	private final JPanel contentPanel;
	private JTextField textUsername;
	private JPasswordField textPassword;

	private String query = null;
	private Connection con = null;
	private PreparedStatement ps = null;


	public Register(){
		contentPanel = new JPanel();
		this.display();
	}

	public void display() {
		this.setTitle("Register");
		this.setModal(true);

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setBounds(59, 73, 65, 14);
		contentPanel.add(lblUsername);
		{
			JLabel lblNewLabel = new JLabel("password");
			lblNewLabel.setBounds(59, 128, 79, 14);
			contentPanel.add(lblNewLabel);
		}
		
		textUsername = new JTextField(15);
		textUsername.setBounds(187, 70, 86, 20);
		contentPanel.add(textUsername);

		textPassword = new JPasswordField(15);
		textPassword.setBounds(187, 125, 86, 20);
		contentPanel.add(textPassword);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Register");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						String username  = textUsername.getText();
						String password = String.valueOf(textPassword.getPassword());
						if(username.isEmpty()||password.isEmpty())
						{
							JOptionPane.showMessageDialog(contentPanel,"SOMETHING MISSING");
						}
						else
						{
							try
							{
								con= Database.connect();
								query = "INSERT INTO logs(login,pass) VALUES (?,?)";
								ps = con.prepareStatement(query);
								ps.setString(1, username);
								ps.setString(2, password);
								ps.execute();
								JOptionPane.showMessageDialog(contentPanel , "Success");
								dispose();
							}
							catch(SQLException e1)
							{
								JOptionPane.showMessageDialog(contentPanel , e1);

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
