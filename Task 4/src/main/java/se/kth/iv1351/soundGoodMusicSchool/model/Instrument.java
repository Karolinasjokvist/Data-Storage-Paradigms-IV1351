package main.java.se.kth.iv1351.soundGoodMusicSchool.model;

public class Instrument {
    private int instrumentId;
    private String type;
    private String brand;
    private int price;
    private Boolean isAvailable;

    public Instrument(int instrumentId, String type, String brand, int price, Boolean isAvailable) {
        this.instrumentId = instrumentId;
        this.type = type;
        this.brand = brand;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public int getInstrumentId() {
        return instrumentId;
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

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "brand='" + brand + '\'' +
                ", price=" + price +
                '}';
    }
}
