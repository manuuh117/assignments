public class Car {
    private String licensePlate;
    private String make;
    private String model;
    private int year;
    private double rentalPricePerDay;
    private boolean isAvailable;

    public Car(String licensePlate, String make, String model, int year, double rentalPricePerDay) {
        this.licensePlate = licensePlate;
        this.make = make;
        this.model = model;
        this.year = year;
        this.rentalPricePerDay = rentalPricePerDay;
        this.isAvailable = true;
    }

    // Getters and setters
    public String getLicensePlate() { return licensePlate; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public double getRentalPricePerDay() { return rentalPricePerDay; }
    public boolean isAvailable() { return isAvailable; }
    
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        return year + " " + make + " " + model + " (" + licensePlate + ") - $" + rentalPricePerDay + "/day";
    }
}