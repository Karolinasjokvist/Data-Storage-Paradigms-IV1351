package main.java.se.kth.iv1351.soundGoodMusicSchool.integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import main.java.se.kth.iv1351.soundGoodMusicSchool.integration.Login;

public class InstrumentDAO {
    private void accessDB() {
        try {
            // Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/SoundGoodMusicSchool", "root", Login.PASSWORD);
            System.out.println("Connection successful");
            
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
