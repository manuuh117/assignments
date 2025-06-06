import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentalAgency {
    private List<Car> fleet;
    private List<Customer> customers;
    private List<RentalTransaction> transactions;
    private int transactionCounter;

    public RentalAgency() {
        fleet = new ArrayList<>();
        customers = new ArrayList<>();
        transactions = new ArrayList<>();
        transactionCounter = 1;
    }

    // Car management
    public void addCar(Car car) {
        fleet.add(car);
    }

    public void removeCar(String licensePlate) {
        fleet.removeIf(car -> car.getLicensePlate().equals(licensePlate));
    }

    // Customer management
    public void registerCustomer(Customer customer) {
        customers.add(customer);
    }

    public Customer findCustomerById(String customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }

    // Rental operations
    public RentalTransaction rentCar(String licensePlate, String customerId, 
                                    LocalDate startDate, LocalDate endDate) {
        Car carToRent = null;
        for (Car car : fleet) {
            if (car.getLicensePlate().equals(licensePlate) && car.isAvailable()) {
                carToRent = car;
                break;
            }
        }
        
        Customer customer = findCustomerById(customerId);
        
        if (carToRent == null || customer == null) {
            return null;
        }
        
        carToRent.setAvailable(false);
        RentalTransaction transaction = new RentalTransaction(
            "T" + transactionCounter++, carToRent, customer, startDate, endDate
        );
        transactions.add(transaction);
        customer.addRentalTransaction(transaction);
        
        return transaction;
    }

    public boolean returnCar(String licensePlate) {
        for (Car car : fleet) {
            if (car.getLicensePlate().equals(licensePlate) && !car.isAvailable()) {
                car.setAvailable(true);
                return true;
            }
        }
        return false;
    }

    // Reporting
    public List<Car> getAvailableCars() {
        List<Car> availableCars = new ArrayList<>();
        for (Car car : fleet) {
            if (car.isAvailable()) {
                availableCars.add(car);
            }
        }
        return availableCars;
    }

    public List<Car> getRentedCars() {
        List<Car> rentedCars = new ArrayList<>();
        for (Car car : fleet) {
            if (!car.isAvailable()) {
                rentedCars.add(car);
            }
        }
        return rentedCars;
    }

    public List<RentalTransaction> getCustomerRentalHistory(String customerId) {
        Customer customer = findCustomerById(customerId);
        return (customer != null) ? customer.getRentalHistory() : new ArrayList<>();
    }
}