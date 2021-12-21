package main.java.se.kth.iv1351.soundGoodMusicSchool.controller;

import java.util.List;

import main.java.se.kth.iv1351.soundGoodMusicSchool.model.*;
import main.java.se.kth.iv1351.soundGoodMusicSchool.integration.*;

public class Controller {
    private final InstrumentDAO instrumentDb;

    public Controller() throws InstrumentDBException {
        instrumentDb = new InstrumentDAO();
    }

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

    public List<? extends InstrumentDTO> listInstrument(String instrumentType) throws InstrumentException {
        try {
            return (List<? extends InstrumentDTO>) instrumentDb.listInstrument(instrumentType);
        } catch (Exception e) {
            throw new InstrumentException("Could not list instruments", e);
        }
    }
}
