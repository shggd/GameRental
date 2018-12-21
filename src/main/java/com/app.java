package com;

import com.login.LoginPage;
import java.awt.EventQueue;
public class app {

    /**
     * Launch the application.
     */


    public static void main(String[] args) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        LoginPage login = new LoginPage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
    }


}
