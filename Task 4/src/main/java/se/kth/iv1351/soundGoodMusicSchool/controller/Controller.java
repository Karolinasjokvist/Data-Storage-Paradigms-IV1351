package main.java.se.kth.iv1351.soundGoodMusicSchool.controller;

import java.util.List;

import main.java.se.kth.iv1351.soundGoodMusicSchool.model.*;
import main.java.se.kth.iv1351.soundGoodMusicSchool.integration.*;

/**
 * The controller class is responsible for passing information between the model and the view.
 */
public class Controller {
    private final InstrumentDAO instrumentDb;

    /**
     * Creates a new instance of the controller.
     *
     * @throws InstrumentDBException if the database could not be accessed.
     */
    public Controller() throws InstrumentDBException {
        instrumentDb = new InstrumentDAO();
    }

    /**
     * Rents an instrument.
     * 
     * @param instrumentId the id of the instrument to rent.
     * @param studentId the id of the student renting the instrument.
     * @throws InstrumentDBException if the database could not be accessed.
     */
    public void rentInstrument(int instrumentId, int studentId) throws InstrumentException {
        String errorMessage = "Could not rent instrument for: " + studentId;

        if (instrumentId < 0 || studentId < 0) {
            throw new InstrumentException(errorMessage);
        }
        try {
            instrumentDb.rentInstrument(instrumentId, studentId);
        } catch (Exception e) {
            throw new InstrumentException(errorMessage, e);
        }
    }

    /**
     * Terminates a rental of an instrument.
     * 
     * @param rentalId the id of the rental to terminate.
     * @throws InstrumentDBException if the database could not be accessed.
    */
    public void terminateRental(int rentalId) throws InstrumentException {
        String errorMessage = "Could not terminate rental for: " + rentalId;

        if (rentalId < 0) {
            throw new InstrumentException(errorMessage);
        }
        try {
            instrumentDb.terminateRental(rentalId);
        } catch (Exception e) {
            throw new InstrumentException(errorMessage, e);
        }
    }

    /**
     * Gets a list of all instruments.
     *
     * @param instrumentType the type of instrument to get.
     * @return a list of all instruments.
     * @throws InstrumentDBException if the database could not be accessed.
     */
    public List<? extends InstrumentDTO> listInstrument(String instrumentType) throws InstrumentException {
        try {
            return (List<? extends InstrumentDTO>) instrumentDb.listInstrument(instrumentType);
        } catch (Exception e) {
            throw new InstrumentException("Could not list instruments", e);
        }
    }
}
