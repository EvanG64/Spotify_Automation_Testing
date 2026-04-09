package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    private final By searchLink = By.cssSelector("a[href*='search']");
    private final By libraryLink = By.cssSelector("a[href*='collection']");
    private final By premiumLink = By.cssSelector("a[href*='premium']");
    private final By loginButton = By.cssSelector("a[href*='login']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public SearchPage navigateToSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchLink)).click();
        return new SearchPage(driver);
    }

    public void navigateToLibrary() {
        wait.until(ExpectedConditions.elementToBeClickable(libraryLink)).click();
    }

    public void navigateToPremium() {
        wait.until(ExpectedConditions.elementToBeClickable(premiumLink)).click();
    }

    public LoginPage clickLoginNav() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        return new LoginPage(driver);
    }
}