package main.java.se.kth.iv1351.soundGoodMusicSchool.model;

/**
 * InstrumentException is thrown if a problem occurs with an instrument.
 */
public class InstrumentException extends Exception {
    public InstrumentException(String message) {
        super(message);
    }

    public InstrumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
