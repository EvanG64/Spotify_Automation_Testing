package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ErrorHandlingPage extends BasePage {
    private final By errorHeader = By.tagName("h1");
    private final By homeLink = By.linkText("Go back to Home");
    private final By offlineBanner = By.cssSelector(".offline-status");

    public ErrorHandlingPage(WebDriver driver) {
        super(driver);
    }

    public String getErrorHeaderText() {
        return driver.findElement(errorHeader).getText();
    }

    public void clickBackToHome() {
        clickElement(driver.findElement(homeLink));
    }

    public boolean isOfflineBannerVisible() {
        return driver.findElement(offlineBanner).isDisplayed();
    }
}