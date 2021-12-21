package main.java.se.kth.iv1351.soundGoodMusicSchool.integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import main.java.se.kth.iv1351.soundGoodMusicSchool.integration.Login;
import main.java.se.kth.iv1351.soundGoodMusicSchool.model.InstrumentDTO;
import main.java.se.kth.iv1351.soundGoodMusicSchool.model.Instrument;
import main.java.se.kth.iv1351.soundGoodMusicSchool.integration.InstrumentDBException;

public class InstrumentDAO {
    private Connection connection;
    private PreparedStatement listInstruments;
    private PreparedStatement rentInstrument;
    private PreparedStatement updateInstrument;
    private PreparedStatement terminateRental;
    private PreparedStatement updateRentalInstrument;
    private PreparedStatement getInstrumentId;

    public InstrumentDAO() throws InstrumentDBException {
        try {
            accessDB();
            prepareStatement();
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

    private void prepareStatement() throws SQLException {
        listInstruments = connection.prepareStatement("SELECT * FROM instrument WHERE type = ? AND is_available = true");
        rentInstrument = connection.prepareStatement("UPDATE instrument SET is_available = false WHERE instrument_id = ?");
        updateInstrument = connection.prepareStatement("INSERT INTO renting_instrument (time_rented, student_id, is_terminated, instrument_id) VALUES (?, ?, ?, ?)");
        terminateRental = connection.prepareStatement("UPDATE instrument SET is_available = true WHERE instrument_id = ?");
        updateRentalInstrument = connection.prepareStatement("UPDATE renting_instrument SET is_terminated = False WHERE rental_id = ?");
        getInstrumentId = connection.prepareStatement("SELECT instrument_id FROM renting_instrument WHERE rental_id = ?");
    }

    public void rentInstrument(int instrumentId, int studentId) throws InstrumentDBException {
        String errorMessage = "Could not rent instrument for: " + studentId;

        try {
            if (instrumentId < 0 || studentId < 0) {
                throw new InstrumentDBException(errorMessage);
            }
            rentInstrument.setInt(1, instrumentId);
            int updatedRows = rentInstrument.executeUpdate();

            if (updatedRows != 1) {
                handleException(errorMessage, null);
            }

            updateInstrument.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            updateInstrument.setInt(2, studentId);
            updateInstrument.setBoolean(3, false);
            updateInstrument.setInt(4, instrumentId);
            updatedRows = updateInstrument.executeUpdate();

            if (updatedRows != 1) {
                handleException(errorMessage, null);
            }

            connection.commit();

        } catch (SQLException e) {
            handleException(errorMessage, e);
        }
    }

    public void terminateRental(int rentalId) throws InstrumentDBException {
        String errorMessage = "Could not terminate rental for: " + rentalId;

        try {
            updateRentalInstrument.setInt(1, rentalId);
            int updatedRows = updateRentalInstrument.executeUpdate();

            if (updatedRows != 1) {
                handleException(errorMessage, null);
            }

            getInstrumentId.setInt(1, rentalId);
            ResultSet result = getInstrumentId.executeQuery();
            result.next();
            int instrumentId = result.getInt("instrument_id");

            terminateRental.setInt(1, instrumentId);
            updatedRows = terminateRental.executeUpdate();

            if (updatedRows != 1) {
                handleException(errorMessage, null);
            }

            connection.commit();

        } catch (SQLException e) {
            handleException(errorMessage, e);
        }
    }

    public List<Instrument> listInstrument(String instrumentType) throws InstrumentDBException {
        String errorMessage = "Could not list instruments";

        List<Instrument> instruments = new ArrayList<>();
        try {
            listInstruments.setString(1, instrumentType);
            ResultSet result = listInstruments.executeQuery();
            
            while (result.next()) {
                instruments.add(new Instrument(result.getInt("instrument_id"),
                                                result.getString("type"),
                                                result.getString("brand"),
                                                result.getInt("price"),
                                                result.getBoolean("is_available")));
            }
            connection.commit();
        } catch (SQLException e) {
            handleException(errorMessage, e);
        }
        return instruments;
    }

    public void handleException(String errorMessage, Exception e) throws InstrumentDBException {
        String completeErrorMessage = errorMessage;

        try {
            connection.rollback();
        } catch (SQLException rollbackException) {
            completeErrorMessage += " Could not rollback transaction" + rollbackException.getMessage();
        }

        if (e != null) {
            throw new InstrumentDBException(errorMessage, e);
        } else {
            throw new InstrumentDBException(errorMessage);
        }
    }
}
