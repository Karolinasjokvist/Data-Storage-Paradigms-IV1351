package main.java.se.kth.iv1351.soundGoodMusicSchool.model;

import java.time.LocalDateTime;

public interface InstrumentDTO {
    public String getType();

    public String getBrand();

    public int getPrice();

    public String getStudentId();

    public LocalDateTime getTimeRented();

    public int getInstrumentId();
}
