package com.aman.medical.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author win7
 */
public class getconMedicalServer {
    public  Connection Con = null;
    public Connection myconnection() throws ClassNotFoundException{
//          try {
//                /* TODO output your page here. You may use following sample code. */
//                Class.forName("com.mysql.jdbc.Driver");
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(getcon.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
             
            try {
                 Class.forName("com.mysql.jdbc.Driver");
                Con = DriverManager.getConnection("jdbc:mysql://192.168.235.76:3306/mi?characterEncoding=UTF8", "root", "123456");
            } catch (SQLException ex) {
                Logger.getLogger(getconMedicalServer.class.getName()).log(Level.SEVERE, null, ex);
            }
           return Con;
    }
}
