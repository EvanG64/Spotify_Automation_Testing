package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    private By usernameField = By.id("login-username");
    private By passwordField = By.id("login-password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.cssSelector("span.Message-sc-15vkh7g-0");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void setUsername(String username) {
        enterText(driver.findElement(usernameField), username);
    }

    public void setPassword(String password) {
        enterText(driver.findElement(passwordField), password);
    }

    public void clickLogin() {
        clickElement(driver.findElement(loginButton));
    }

    public String getErrorMessage() {
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return driver.findElement(errorMessage).getText();
    }
}