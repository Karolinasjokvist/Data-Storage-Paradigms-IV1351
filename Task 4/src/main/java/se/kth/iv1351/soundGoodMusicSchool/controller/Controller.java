package main.java.se.kth.iv1351.soundGoodMusicSchool.controller;

import java.util.ArrayList;
import java.util.List;

import main.java.se.kth.iv1351.soundGoodMusicSchool.model.*;
import main.java.se.kth.iv1351.soundGoodMusicSchool.integration.*;

public class Controller {
    private final InstrumentDAO instrumentDb;

    public Controller() throws InstrumentDBException {
        instrumentDb = new InstrumentDAO();
    }

    public void rentInstrument(String instrumentType, int studentID) throws InstrumentException {
        String errorMessage = "Could not rent instrument for: " + studentID;

        if (instrumentType == null || studentID < 0) {
            throw new InstrumentException(errorMessage);
        }
        try {
            instrumentDb.rentInstrument(instrumentType, studentID);
        } catch (Exception e) {
            throw new InstrumentException(errorMessage, e);
        }
    }

    public void terminateRental(int instrumentId) throws InstrumentException {
        String errorMessage = "Could not terminate rental for: " + instrumentId;

        if (instrumentId < 0) {
            throw new InstrumentException(errorMessage);
        }
        try {
            instrumentDb.terminateRental(instrumentId);
        } catch (Exception e) {
            throw new InstrumentException(errorMessage, e);
        }
    }

    public List<? extends InstrumentDTO> listInstrument() throws InstrumentException {

        try {
            return instrumentDb.listInstrument();
        } catch (Exception e) {
            throw new InstrumentException("Could not list instruments", e);
        }
    }
}
