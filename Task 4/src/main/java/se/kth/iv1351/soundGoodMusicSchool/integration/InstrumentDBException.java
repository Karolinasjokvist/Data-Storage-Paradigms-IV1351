package main.java.se.kth.iv1351.soundGoodMusicSchool.integration;

/**
 * This exception is thrown when an error occurs in the database.
 */
public class InstrumentDBException extends Exception {
    public InstrumentDBException(String message) {
        super(message);
    }

    public InstrumentDBException(String message, Throwable cause) {
        super(message, cause);
    }
}
