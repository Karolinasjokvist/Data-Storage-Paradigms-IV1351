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

import main.java.se.kth.iv1351.soundGoodMusicSchool.model.Instrument;

public class InstrumentDAO {
    private static final String INSTRUMENT_TABLE_NAME = "Instruments";
    private static final String INSTRUMENT_PK_COLUMN_NAME = "instrument_id";
    private static final String INSTRUMENT_AVAILABLE_COLUMN_NAME = "is_available";
    private static final String INSTRUMENT_TYPE_COLUMN_NAME = "type";
    private static final String INSTRUMENT_BRAND_COLUMN_NAME = "brand";
    private static final String INSTRUMENT_PRICE_COLUMN_NAME = "price";

    private static final String RENT_INSTRUMENT_TABLE_NAME = "renting_instruments";
    private static final String RENT_INSTRUMENT_PK_COLUMN_NAME = "rental_id";
    private static final String RENT_INSTRUMENT_START_COLUMN_NAME = "time_rented";
    private static final String RENT_INSTRUMENT_FK_STUDENT_COLUMN_NAME = "student_id";
    private static final String RENT_INSTRUMENT_FK_INSTRUMENT_COLUMN_NAME = "instrument_id";
    private static final String RENT_INSTRUMENT_TERMINATED_COLUMN_NAME = "is_terminated";

    private static final String STUDENT_TABLE_NAME = "student";
    private static final String STUDENT_PK_COLUMN_NAME = "student_id";

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
    private PreparedStatement validateRentalId;

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
        listInstruments = connection.prepareStatement("SELECT * FROM " + INSTRUMENT_TABLE_NAME + " WHERE " + INSTRUMENT_TYPE_COLUMN_NAME + " = ? AND " + INSTRUMENT_AVAILABLE_COLUMN_NAME + " = true");
        rentInstrument = connection.prepareStatement("UPDATE " + INSTRUMENT_TABLE_NAME + " SET " + INSTRUMENT_AVAILABLE_COLUMN_NAME + " = false WHERE " + INSTRUMENT_PK_COLUMN_NAME + " = ?");
        updateInstrument = connection.prepareStatement("INSERT INTO " + RENT_INSTRUMENT_TABLE_NAME + " (" + RENT_INSTRUMENT_PK_COLUMN_NAME + ", " + RENT_INSTRUMENT_START_COLUMN_NAME + ", " + RENT_INSTRUMENT_FK_STUDENT_COLUMN_NAME + ", " + RENT_INSTRUMENT_TERMINATED_COLUMN_NAME + ", " + RENT_INSTRUMENT_FK_INSTRUMENT_COLUMN_NAME + ") VALUES (?, ?, ?, ?, ?)");
        terminateRental = connection.prepareStatement("UPDATE "+ INSTRUMENT_TABLE_NAME +" SET " + INSTRUMENT_AVAILABLE_COLUMN_NAME + " = true WHERE " + INSTRUMENT_PK_COLUMN_NAME + " = ?");
        updateRentalInstrument = connection.prepareStatement("UPDATE " + RENT_INSTRUMENT_TABLE_NAME + " SET " + RENT_INSTRUMENT_TERMINATED_COLUMN_NAME + " = true WHERE " + RENT_INSTRUMENT_PK_COLUMN_NAME + " = ?");
        getInstrumentId = connection.prepareStatement("SELECT " + INSTRUMENT_PK_COLUMN_NAME + " FROM " + RENT_INSTRUMENT_TABLE_NAME + " WHERE " + RENT_INSTRUMENT_PK_COLUMN_NAME + " = ?");

        getNumberOfRentals = connection.prepareStatement("SELECT COUNT(*) FROM " + RENT_INSTRUMENT_TABLE_NAME);
        validateStudentId = connection.prepareStatement("SELECT " + STUDENT_PK_COLUMN_NAME + " FROM " + STUDENT_TABLE_NAME + " WHERE " + STUDENT_PK_COLUMN_NAME + " = ?");
        validateInstrumentId = connection.prepareStatement("SELECT " + INSTRUMENT_PK_COLUMN_NAME + " FROM " + INSTRUMENT_TABLE_NAME + " WHERE " + INSTRUMENT_PK_COLUMN_NAME + " = ?");
        getStudentRentalCount = connection.prepareStatement("SELECT COUNT(*) FROM " + RENT_INSTRUMENT_TABLE_NAME + " WHERE " + RENT_INSTRUMENT_FK_STUDENT_COLUMN_NAME + " = ? AND " + RENT_INSTRUMENT_TERMINATED_COLUMN_NAME + " = false");
        validateRentalId = connection.prepareStatement("SELECT " + RENT_INSTRUMENT_PK_COLUMN_NAME + " FROM " + RENT_INSTRUMENT_TABLE_NAME + " WHERE " + RENT_INSTRUMENT_PK_COLUMN_NAME + " = ?");
    }

    public void rentInstrument(int instrumentId, int studentId) throws InstrumentDBException {
        String errorMessage = "Could not rent instrument for: " + studentId;

        try {
            if (!validInstrumentId(instrumentId) || !validStudentId(studentId) || !validStudentRentalCount(studentId)) {
                handleException(errorMessage, null);
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
            if (!validRentalId(rentalId)) {
                handleException(errorMessage, null);
            }

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
                instruments.add(new Instrument(result.getInt(INSTRUMENT_PK_COLUMN_NAME),
                                                result.getString(INSTRUMENT_TYPE_COLUMN_NAME),
                                                result.getString(INSTRUMENT_BRAND_COLUMN_NAME),
                                                result.getInt(INSTRUMENT_PRICE_COLUMN_NAME),
                                                result.getBoolean(INSTRUMENT_AVAILABLE_COLUMN_NAME)));
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

    private boolean validRentalId(int rentalId) throws SQLException {
        validateRentalId.setInt(1, rentalId);
        ResultSet result = validateRentalId.executeQuery();
        if (result.next()) {
            return true;
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
