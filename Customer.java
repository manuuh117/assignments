import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String customerId;
    private String name;
    private String contactInfo;
    private List<RentalTransaction> rentalHistory;
    

    public Customer(String customerId, String name, String contactInfo) {
        this.customerId = customerId;
        this.name = name;
        this.contactInfo = contactInfo;
        this.rentalHistory = new ArrayList<>();
    }
    

    // Getters
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getContactInfo() { return contactInfo; }
    public List<RentalTransaction> getRentalHistory() { return rentalHistory; }
    
    public void addRentalTransaction(RentalTransaction transaction) {
        rentalHistory.add(transaction);
    }

    @Override
    public String toString() {
        return name + " (ID: " + customerId + ") - Contact: " + contactInfo;
    }
}