package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emailField = By.cssSelector(
            "input[id='login-username'], input[name='username'], input[type='email'], input[autocomplete='username']"
    );
    private final By continueButton = By.cssSelector(
            "button[id='login-button'], button[data-testid='login-button'], button[type='submit']"
    );
    private final By logInWithPasswordLink = By.xpath(
            "//*[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'log in with a password')]" +
                    " | //*[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'log in with password')]"
    );
    private final By passwordField = By.cssSelector(
            "input[type='password'], input[id='login-password'], input[name='password']"
    );
    private final By logInButton = By.cssSelector(
            "button[id='login-button'], button[data-testid='login-button'], button[type='submit']"
    );

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void waitForPage() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(emailField),
                ExpectedConditions.visibilityOfElementLocated(continueButton)
        ));
    }

    public boolean isEmailFieldVisible() {
        try { return wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    public boolean isContinueButtonVisible() {
        try { return wait.until(ExpectedConditions.visibilityOfElementLocated(continueButton)).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    public boolean isPasswordFieldVisible() {
        try { return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    public void setEmail(String email) {
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(emailField));
        field.click();
        field.clear();
        field.sendKeys(email);
    }

    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }

    public void clickLogInWithPassword() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(logInWithPasswordLink)).click();
        } catch (Exception e) { /* already on password screen */ }
    }

    public void setPassword(String password) {
        try {
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));

            // Click the field first to focus it
            field.click();
            Thread.sleep(300);

            // Use Actions to type naturally — prevents React from clearing the field
            Actions actions = new Actions(driver);
            actions.moveToElement(field).click().perform();
            Thread.sleep(200);

            // Select all and delete any existing content
            actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
            Thread.sleep(100);
            actions.sendKeys(Keys.DELETE).perform();
            Thread.sleep(200);

            // Type password character by character
            for (char c : password.toCharArray()) {
                actions.sendKeys(String.valueOf(c)).perform();
                Thread.sleep(75);
            }

            Thread.sleep(300);

            // Verify the value was typed correctly
            String typed = field.getAttribute("value");
            if (typed == null || typed.isEmpty() || !typed.equals(password)) {
                // Fallback: use JavaScript to set the value React-style
                ((JavascriptExecutor) driver).executeScript(
                        "var el = arguments[0];" +
                                "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(" +
                                "window.HTMLInputElement.prototype, 'value').set;" +
                                "nativeInputValueSetter.call(el, arguments[1]);" +
                                "el.dispatchEvent(new Event('input', {bubbles: true}));" +
                                "el.dispatchEvent(new Event('change', {bubbles: true}));",
                        field, password
                );
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void clickLogIn() {
        wait.until(ExpectedConditions.elementToBeClickable(logInButton)).click();
    }

    public String getPageText() {
        return driver.findElement(By.tagName("body")).getText();
    }
}