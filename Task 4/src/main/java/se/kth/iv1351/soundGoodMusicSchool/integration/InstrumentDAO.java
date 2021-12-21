package main.java.se.kth.iv1351.soundGoodMusicSchool.integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.java.se.kth.iv1351.soundGoodMusicSchool.integration.Login;
import main.java.se.kth.iv1351.soundGoodMusicSchool.model.InstrumentDTO;
import main.java.se.kth.iv1351.soundGoodMusicSchool.model.Instrument;
import main.java.se.kth.iv1351.soundGoodMusicSchool.integration.InstrumentDBException;

public class InstrumentDAO {
    private Connection connection;

    public InstrumentDAO() throws InstrumentDBException {
        try {
            accessDB();
            //TODO: prepareStatement();

        } catch (Exception e) {
            throw new InstrumentDBException("Could not connect to database", e);
        }
    }

    private void accessDB() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/SoundGoodMusicSchool", "postgres", Login.PASSWORD);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void rentInstrument(String instrumentType, int studentID) throws InstrumentDBException {
        String errorMessage = "Could not rent instrument for: " + studentID;
    }

    public void terminateRental(int InstrumentId) throws InstrumentDBException {
        String errorMessage = "Could not terminate rental for: " + InstrumentId;
    }

    public List<? extends InstrumentDTO> listInstrument() throws InstrumentDBException {
        String errorMessage = "Could not list instruments";
        List<Instrument> instruments = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery("SELECT * FROM instrument");
        } catch (SQLException e) {
            System.out.println("Could not list instruments");
        }
        return null;
    }
}
