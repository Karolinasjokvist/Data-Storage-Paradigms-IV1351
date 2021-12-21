package main.java.se.kth.iv1351.soundGoodMusicSchool.model;

import java.time.LocalDateTime;

public class Instrument {
    private int instrumentId;
    private LocalDateTime timeRented;
    private String type;
    private String brand;
    private int price;
    private String studentId;

    public Instrument(int instrumentId, LocalDateTime timeRented, String type, String brand, int price, String studentId) {
        this.instrumentId = instrumentId;
        this.timeRented = timeRented;
        this.type = type;
        this.brand = brand;
        this.price = price;
        this.studentId = studentId;
    }

    public int getInstrumentId() {
        return instrumentId;
    }

    public LocalDateTime getTimeRented() {
        return timeRented;
    }

    public String getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }

    public int getPrice() {
        return price;
    }

    public String getStudentId() {
        return studentId;
    }

    public void rentInstrument(String studentId) throws InstrumentException {
        if (studentId == null || Integer.parseInt(studentId) < 0) {
            throw new InstrumentException("Could not rent instrument for: " + studentId);
        }

        this.studentId = studentId;
        this.timeRented = LocalDateTime.now();
    }

    public void terminateRental() {
        this.studentId = null;
        this.timeRented = null;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "instrumentId=" + instrumentId +
                ", timeRented=" + timeRented +
                ", type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", studentId='" + studentId + '\'' +
                '}';
    }
}
