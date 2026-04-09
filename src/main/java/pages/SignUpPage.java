package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SignUpPage extends BasePage {

    private By emailField = By.cssSelector("input[type='email'], input");
    private By passwordField = By.cssSelector("input[type='password']");
    private By signUpButton = By.xpath("//button[contains(.,'Sign up') or contains(.,'Continue')]");

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    public void enterEmail(String email) {
        List<WebElement> fields = driver.findElements(emailField);
        if (!fields.isEmpty()) {
            enterText(fields.get(0), email);
        }
    }

    public void enterPassword(String password) {
        List<WebElement> fields = driver.findElements(passwordField);
        if (!fields.isEmpty()) {
            enterText(fields.get(0), password);
        }
    }

    public void clickSignUp() {
        List<WebElement> buttons = driver.findElements(signUpButton);
        if (!buttons.isEmpty()) {
            clickElement(buttons.get(0));
        }
    }

    public boolean isEmailFieldVisible() {
        List<WebElement> fields = driver.findElements(emailField);
        return !fields.isEmpty() && fields.get(0).isDisplayed();
    }
}

