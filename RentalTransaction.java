import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RentalTransaction {
    private String transactionId;
    private Car car;
    private Customer customer;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalCost;

    public RentalTransaction(String transactionId, Car car, Customer customer, 
                             LocalDate startDate, LocalDate endDate) {
        this.transactionId = transactionId;
        this.car = car;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        calculateTotalCost();
    }

    private void calculateTotalCost() {
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        totalCost = days * car.getRentalPricePerDay();
    }

    // Getters
    public String getTransactionId() { return transactionId; }
    public Car getCar() { return car; }
    public Customer getCustomer() { return customer; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public double getTotalCost() { return totalCost; }

    @Override
    public String toString() {
        return "Transaction #" + transactionId + ": " + customer.getName() + " rented " + 
               car.getMake() + " " + car.getModel() + " for $" + totalCost;
    }
}