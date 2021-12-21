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
    private PreparedStatement getNumberOfRentals;
    private PreparedStatement validateStudentId;
    private PreparedStatement validateInstrumentId;
    private PreparedStatement getStudentRentalCount;

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
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void prepareStatement() throws SQLException {
        listInstruments = connection.prepareStatement("SELECT * FROM instruments WHERE type = ? AND is_available = true");
        rentInstrument = connection.prepareStatement("UPDATE instruments SET is_available = false WHERE instrument_id = ?");
        updateInstrument = connection.prepareStatement("INSERT INTO renting_instruments (rental_id, time_rented, student_id, is_terminated, instrument_id) VALUES (?, ?, ?, ?, ?)");
        terminateRental = connection.prepareStatement("UPDATE instruments SET is_available = true WHERE instrument_id = ?");
        updateRentalInstrument = connection.prepareStatement("UPDATE renting_instruments SET is_terminated = False WHERE rental_id = ?");
        getInstrumentId = connection.prepareStatement("SELECT instrument_id FROM renting_instruments WHERE rental_id = ?");

        getNumberOfRentals = connection.prepareStatement("SELECT COUNT(*) FROM renting_instruments");
        validateStudentId = connection.prepareStatement("SELECT student_id FROM student WHERE student_id = ?");
        validateInstrumentId = connection.prepareStatement("SELECT instrument_id FROM instruments WHERE instrument_id = ?");
        getStudentRentalCount = connection.prepareStatement("SELECT COUNT(*) FROM renting_instruments WHERE student_id = ? AND is_terminated = false");
    }

    public void rentInstrument(int instrumentId, int studentId) throws InstrumentDBException {
        String errorMessage = "Could not rent instrument for: " + studentId;

        try {
            if (!validInstrumentId(instrumentId) || !validStudentId(studentId) || !validStudentRentalCount(studentId)) {
                throw new InstrumentDBException(errorMessage);
            }

            rentInstrument.setInt(1, instrumentId);
            int updatedRows = rentInstrument.executeUpdate();

            if (updatedRows != 1) {
                handleException(errorMessage, null);
            }

            updateInstrument.setInt(1, rental_id());
            updateInstrument.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            updateInstrument.setInt(3, studentId);
            updateInstrument.setBoolean(4, false);
            updateInstrument.setInt(5, instrumentId);
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
    
    private int rental_id() throws SQLException {
        ResultSet result = getNumberOfRentals.executeQuery();
        result.next();
        int id = result.getInt(1) + 1;
        return id;
    }

    private boolean validStudentId(int studentId) throws SQLException {
        validateStudentId.setInt(1, studentId);
        ResultSet result = validateStudentId.executeQuery();
        if (result.next()) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean validInstrumentId(int instrumentId) throws SQLException {
        validateInstrumentId.setInt(1, instrumentId);
        ResultSet result = validateInstrumentId.executeQuery();
        if (result.next()) {
            return true;
        } else {
            return false;
        }      
    }

    private boolean validStudentRentalCount(int studentId) throws SQLException {
        getStudentRentalCount.setInt(1, studentId);
        ResultSet result = getStudentRentalCount.executeQuery();

        if (result.next()) {
            int count = result.getInt(1);
            if (count < 3) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }      
    }

    public void handleException(String errorMessage, Exception e) throws InstrumentDBException {
        String completeErrorMessage = errorMessage;

        try {
            connection.rollback();
        } catch (SQLException rollbackException) {
            completeErrorMessage += " Could not rollback transaction" + rollbackException.getMessage();
        }

        if (e != null) {
            throw new InstrumentDBException(completeErrorMessage, e);
        } else {
            throw new InstrumentDBException(completeErrorMessage);
        }
    }
}
