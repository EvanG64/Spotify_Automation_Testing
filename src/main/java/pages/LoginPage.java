package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;

    private final By emailField = By.cssSelector("input");
    private final By continueButton = By.xpath("//button[contains(.,'Continue')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void waitForPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        wait.until(ExpectedConditions.visibilityOfElementLocated(continueButton));
    }

    public void setEmail(String email) {
        WebElement emailBox = wait.until(ExpectedConditions.elementToBeClickable(emailField));
        emailBox.click();
        emailBox.clear();
        emailBox.sendKeys(email);
    }

    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }

    public boolean isEmailFieldVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).isDisplayed();
    }

    public boolean isContinueButtonVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(continueButton)).isDisplayed();
    }

    public String getPageText() {
        return driver.findElement(By.tagName("body")).getText();
    }
}