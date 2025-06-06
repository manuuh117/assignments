import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class CarRentalSystem {
    private static RentalAgency agency = new RentalAgency();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE;

    public static void main(String[] args) {
        initializeSampleData();
        
        while (true) {
            System.out.println("\n===== Car Rental System =====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Add New Car to Fleet");
            System.out.println("4. Register New Customer");
            System.out.println("5. View Available Cars");
            System.out.println("6. View Rented Cars");
            System.out.println("7. View Customer Rental History");
            System.out.println("8. Exit");
            System.out.print("Select option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            
            switch (choice) {
                case 1:
                    rentCarProcess();
                    break;
                case 2:
                    returnCarProcess();
                    break;
                case 3:
                    addCarProcess();
                    break;
                case 4:
                    registerCustomerProcess();
                    break;
                case 5:
                    displayAvailableCars();
                    break;
                case 6:
                    displayRentedCars();
                    break;
                case 7:
                    viewCustomerHistory();
                    break;
                case 8:
                    System.out.println("Exiting system...");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void initializeSampleData() {
        // Add sample cars
        agency.addCar(new Car("ABC123", "Toyota", "Camry", 2022, 50.0));
        agency.addCar(new Car("XYZ789", "Honda", "Civic", 2023, 45.0));
        agency.addCar(new Car("DEF456", "Ford", "Mustang", 2021, 75.0));
        
        // Register sample customers
        agency.registerCustomer(new Customer("C1001", "John Doe", "john@example.com"));
        agency.registerCustomer(new Customer("C1002", "Jane Smith", "jane@example.com"));
    }

    private static void rentCarProcess() {
        System.out.println("\n--- Rent a Car ---");
        
        // Display available cars
        List<Car> availableCars = agency.getAvailableCars();
        if (availableCars.isEmpty()) {
            System.out.println("No cars available for rent.");
            return;
        }
        
        System.out.println("Available Cars:");
        for (int i = 0; i < availableCars.size(); i++) {
            System.out.println((i + 1) + ". " + availableCars.get(i));
        }
        
        // Select car
        System.out.print("Select car (number): ");
        int carIndex = scanner.nextInt() - 1;
        scanner.nextLine();  // Consume newline
        
        if (carIndex < 0 || carIndex >= availableCars.size()) {
            System.out.println("Invalid car selection.");
            return;
        }
        String licensePlate = availableCars.get(carIndex).getLicensePlate();
        
        // Get customer ID
        System.out.print("Enter customer ID: ");
        String customerId = scanner.nextLine();
        
        // Get rental dates
        LocalDate startDate = getDateInput("Enter start date (yyyy-mm-dd): ");
        LocalDate endDate = getDateInput("Enter end date (yyyy-mm-dd): ");
        
        if (startDate == null || endDate == null || endDate.isBefore(startDate)) {
            System.out.println("Invalid dates. Rental failed.");
            return;
        }
        
        // Process rental
        RentalTransaction transaction = agency.rentCar(licensePlate, customerId, startDate, endDate);
        
        if (transaction != null) {
            System.out.println("Rental successful!");
            System.out.println("Transaction ID: " + transaction.getTransactionId());
            System.out.println("Total cost: $" + transaction.getTotalCost());
        } else {
            System.out.println("Rental failed. Please check car availability and customer ID.");
        }
    }

    private static void returnCarProcess() {
        System.out.println("\n--- Return a Car ---");
        System.out.print("Enter car license plate: ");
        String licensePlate = scanner.nextLine();
        
        boolean success = agency.returnCar(licensePlate);
        
        if (success) {
            System.out.println("Car returned successfully.");
        } else {
            System.out.println("Return failed. Car not found or already returned.");
        }
    }

    private static void addCarProcess() {
        System.out.println("\n--- Add New Car ---");
        System.out.print("License Plate: ");
        String licensePlate = scanner.nextLine();
        
        System.out.print("Make: ");
        String make = scanner.nextLine();
        
        System.out.print("Model: ");
        String model = scanner.nextLine();
        
        System.out.print("Year: ");
        int year = scanner.nextInt();
        
        System.out.print("Daily Rental Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();  // Consume newline
        
        agency.addCar(new Car(licensePlate, make, model, year, price));
        System.out.println("Car added to fleet.");
    }

    private static void registerCustomerProcess() {
        System.out.println("\n--- Register New Customer ---");
        System.out.print("Customer ID: ");
        String customerId = scanner.nextLine();
        
        System.out.print("Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Contact Info: ");
        String contact = scanner.nextLine();
        
        agency.registerCustomer(new Customer(customerId, name, contact));
        System.out.println("Customer registered.");
    }

    private static void displayAvailableCars() {
        System.out.println("\n--- Available Cars ---");
        List<Car> cars = agency.getAvailableCars();
        
        if (cars.isEmpty()) {
            System.out.println("No cars available");
            return;
        }
        
        for (Car car : cars) {
            System.out.println(car);
        }
    }

    private static void displayRentedCars() {
        System.out.println("\n--- Rented Cars ---");
        List<Car> cars = agency.getRentedCars();
        
        if (cars.isEmpty()) {
            System.out.println("No cars currently rented");
            return;
        }
        
        for (Car car : cars) {
            System.out.println(car);
        }
    }

    private static void viewCustomerHistory() {
        System.out.println("\n--- Customer Rental History ---");
        System.out.print("Enter customer ID: ");
        String customerId = scanner.nextLine();
        
        List<RentalTransaction> history = agency.getCustomerRentalHistory(customerId);
        
        if (history.isEmpty()) {
            System.out.println("No rental history found");
            return;
        }
        
        System.out.println("Rental History for Customer " + customerId + ":");
        for (RentalTransaction transaction : history) {
            System.out.println("- " + transaction);
        }
    }

    private static LocalDate getDateInput(String prompt) {
        System.out.print(prompt);
        String dateStr = scanner.nextLine();
        
        try {
            return LocalDate.parse(dateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-mm-dd.");
            return null;
        }
    }
}