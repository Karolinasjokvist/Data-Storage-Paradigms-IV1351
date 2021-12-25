package main.java.se.kth.iv1351.soundGoodMusicSchool.model;

/**
 * Instrument represents an instrument in the database
 */
public class Instrument {
    private int instrumentId;
    private String type;
    private String brand;
    private int price;
    private Boolean isAvailable;

    /**
     * Creates a new instance of the instrument.
     *
     * @param instrumentId the id of the instrument.
     * @param type the type of the instrument.
     * @param brand the brand of the instrument.
     * @param price the price of the instrument.
     * @param isAvailable the availability of the instrument.
     */
    public Instrument(int instrumentId, String type, String brand, int price, Boolean isAvailable) {
        this.instrumentId = instrumentId;
        this.type = type;
        this.brand = brand;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    /**
     * Gets the id of the instrument.
     *
     * @return the id of the instrument.
     */
    public int getInstrumentId() {
        return instrumentId;
    }

    /**
     * Gets the type of the instrument.
     *
     * @return the type of the instrument.
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the brand of the instrument.
     *
     * @return the brand of the instrument.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Gets the price of the instrument.
     *
     * @return the price of the instrument.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Gets the availability of the instrument.
     *
     * @return the availability of the instrument.
     */
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    /**
     * Overrides the toString method with a custom string.
     * 
     * @return a custom string.
     */
    @Override
    public String toString() {
        return "Instrument{" +
                "brand='" + brand + '\'' +
                ", price=" + price +
                '}';
    }
}
