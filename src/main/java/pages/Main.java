package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

/**
 * This class serves as a manual entry point to verify the environment setup.
 * Official project execution should be done via testng.xml.
 */
public class Main {
    public static void main(String[] args) {
        // 1. Initialize WebDriver (Selenium 4 handles driver management)
        WebDriver driver = new ChromeDriver();

        try {
            // 2. Basic Browser Configuration
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // 3. Navigate to the System Under Test (Spotify)
            System.out.println("Launching Spotify Automation Suite...");
            driver.get("https://open.spotify.com/");

            // 4. Instantiate the HomePage and verify access
            HomePage homePage = new HomePage(driver);
            System.out.println("Page Title: " + driver.getTitle());

            if (driver.getTitle().contains("Spotify")) {
                System.out.println("SUCCESS: Connection to Spotify established.");
            } else {
                System.out.println("FAILURE: Could not verify Spotify homepage.");
            }

        } catch (Exception e) {
            System.out.println("An error occurred during manual execution: " + e.getMessage());
        } finally {
            // 5. Always close the browser
            System.out.println("Closing test session.");
            driver.quit();
        }
    }
}