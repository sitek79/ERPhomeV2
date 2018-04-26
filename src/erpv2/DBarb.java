package erpv2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBarb {
    private final static String URLFIXED =
            "jdbc:mysql://172.24.225.222/erpdb?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" +
                    "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    //con = DriverManager.getConnection("jdbc:mysql://172.24.225.222/erpdb","erpuser","linAdmin79!!!");
    private final static String USERNAME = "erpuser";
    private final static String PASSWORD = "linAdmin79!!!";
    
    public void dBarb() {

        Connection dbconnect;
        try {
            dbconnect = DriverManager.getConnection(URLFIXED, USERNAME, PASSWORD);
            
        } catch (SQLException ex) {
            Logger.getLogger(DBarb.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!dbconnect.isClosed()) {
            System.out.println("Соединение с БД Установлено!");
        }
        //Работаем с базой
        dbconnect.close();
        if (dbconnect.isClosed()) {
            System.out.println("Соединение с БД Закрыто!");
        }
    }
    
}
