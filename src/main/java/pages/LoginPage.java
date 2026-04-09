package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js = (JavascriptExecutor) driver;
    }

    public void waitForPage() {
        // Wait for page to load - look for any input field
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("input")));
        // Add a small delay for dynamic content to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void setEmail(String email) {
        WebElement emailInput = findEmailField();
        emailInput.click();
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void clickContinue() {
        WebElement continueBtn = findContinueButton();
        continueBtn.click();
        try {
            Thread.sleep(2000); // Wait for password field to appear
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isEmailFieldVisible() {
        try {
            WebElement emailInput = findEmailField();
            return emailInput.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isContinueButtonVisible() {
        try {
            WebElement continueBtn = findContinueButton();
            return continueBtn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPageText() {
        return driver.findElement(By.tagName("body")).getText();
    }

    public void setPassword(String password) {
        WebElement passwordInput = findPasswordField();
        passwordInput.click();
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public boolean isPasswordFieldVisible() {
        try {
            WebElement passwordInput = findPasswordField();
            return passwordInput.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLogIn() {
        WebElement loginBtn = findLogInButton();
        loginBtn.click();
    }

    public boolean isLogInButtonVisible() {
        try {
            WebElement loginBtn = findLogInButton();
            return loginBtn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLogInWithPassword() {
        // After email is submitted, Spotify shows options: "Send code to email" or "Log in with password"
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        for (WebElement button : buttons) {
            String text = button.getText().toLowerCase();
            if (text.contains("log in with password") || text.contains("password") && button.isDisplayed() && button.isEnabled()) {
                button.click();
                try {
                    Thread.sleep(1000); // Wait for password field to appear
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return;
            }
        }
        // If no specific "Log in with password" button found, assume password field is already visible
    }
    // Helper methods to find elements more robustly
    private WebElement findEmailField() {
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        for (WebElement input : inputs) {
            String type = input.getAttribute("type");
            String name = input.getAttribute("name");
            String placeholder = input.getAttribute("placeholder");
            String ariaLabel = input.getAttribute("aria-label");
            
            if ((type == null || type.equals("text") || type.equals("email")) &&
                (name != null && name.contains("username")) ||
                (placeholder != null && placeholder.toLowerCase().contains("email")) ||
                (ariaLabel != null && ariaLabel.toLowerCase().contains("email"))) {
                if (input.isDisplayed()) {
                    return input;
                }
            }
        }
        // Fallback: get first visible input field
        for (WebElement input : inputs) {
            if (input.isDisplayed()) {
                return input;
            }
        }
        throw new RuntimeException("Email field not found");
    }

    private WebElement findPasswordField() {
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        for (WebElement input : inputs) {
            String type = input.getAttribute("type");
            String name = input.getAttribute("name");
            
            if ((type != null && type.equals("password")) ||
                (name != null && name.toLowerCase().contains("password"))) {
                if (input.isDisplayed()) {
                    return input;
                }
            }
        }
        throw new RuntimeException("Password field not found");
    }

    private WebElement findContinueButton() {
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        for (WebElement button : buttons) {
            String text = button.getText().toLowerCase();
            if ((text.contains("continue") || text.contains("next")) && button.isDisplayed() && button.isEnabled()) {
                return button;
            }
        }
        // Fallback: get first visible enabled button
        for (WebElement button : buttons) {
            if (button.isDisplayed() && button.isEnabled()) {
                return button;
            }
        }
        throw new RuntimeException("Continue button not found");
    }

    private WebElement findLogInButton() {
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        for (WebElement button : buttons) {
            String text = button.getText().toLowerCase();
            if ((text.contains("log in") || text.contains("login") || text.contains("sign in")) && 
                button.isDisplayed() && button.isEnabled()) {
                return button;
            }
        }
        // Fallback: get the last visible enabled button
        WebElement lastButton = null;
        for (WebElement button : buttons) {
            if (button.isDisplayed() && button.isEnabled()) {
                lastButton = button;
            }
        }
        if (lastButton != null) {
            return lastButton;
        }
        throw new RuntimeException("Log in button not found");
    }
}