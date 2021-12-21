package main.java.se.kth.iv1351.soundGoodMusicSchool.integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import main.java.se.kth.iv1351.soundGoodMusicSchool.integration.Login;

public class InstrumentDAO {
    private void accessDB() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/SoundGoodMusicSchool", "postgres", Login.PASSWORD);
            System.out.println("Connection successful");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
