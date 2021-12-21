package main.java.se.kth.iv1351.soundGoodMusicSchool.model;

public class RejectedException extends Exception {
    public RejectedException(String message) {
        super(message);
    }

    public RejectedException(String message, Throwable cause) {
        super(message, cause);
    }
}
