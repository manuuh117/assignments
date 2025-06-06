import java.io.Console;
import java.util.Arrays;

public class LoginSystem {
    public static void main(String[] args) {
        // Predefined correct password and username i set them as constants
        final String CORRECT_USERNAME = "kimathi";
        final char[] CORRECT_PASSWORD = "mypassword".toCharArray();

        // checking the console where the passwords and username will display
        Console console = System.console();
        if (console == null) {
            System.err.println("Console is not available. Please run the program in a terminal.");
            System.exit(1);
        }

        int tries = 3;
        boolean loggedIn = false;

        for (int i = 0; i < tries; i++) {
            // Read username
            String username = console.readLine("Enter username: ");

            // Read password ( hidden)
            System.out.print("Enter password: ");
            char[] passwordChars = console.readPassword();
            
            // Display password as stars after entry
            System.out.print("\rEnter password: ");
            for (int j = 0; j < passwordChars.length; j++) {
                System.out.print("*");
            }
            System.out.println();

            // Validate credentials
            if (CORRECT_USERNAME.equals(username) && Arrays.equals(passwordChars, CORRECT_PASSWORD)) {
                loggedIn = true;
                Arrays.fill(passwordChars, ' '); // Clear password from memory
                System.out.println("Login successful!");
                break;
            } else {
                Arrays.fill(passwordChars, ' '); // Clear password from memory
                int remaining = tries - i - 1;
                System.out.println("Incorrect username or password. " +
                        (remaining > 0 ? "You have " + remaining + " tries left." : "No tries left."));
            }
        }

        if (!loggedIn) {
            System.out.println("Login failed. Try again later.");
        }
    }
}