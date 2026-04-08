package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignUpPage extends BasePage {

    // Locators for the Sign Up form
    private By emailField = By.id("email");
    private By passwordField = By.id("password");
    private By displayNameField = By.id("displayname");
    private By dayField = By.id("day");
    private By monthDropdown = By.id("month");
    private By yearField = By.id("year");
    private By genderMaleRadio = By.xpath("//label[@for='gender_option_male']");
    private By termsCheckbox = By.xpath("//label[@for='terms-conditions-checkbox']");
    private By submitButton = By.cssSelector("[data-testid='submit']");
    private By errorBanner = By.cssSelector("div[data-encore-id='banner']");
    private By emailErrorMessage = By.id("email-error");

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    public void enterEmail(String email) {
        enterText(driver.findElement(emailField), email);
    }

    public void enterPassword(String password) {
        enterText(driver.findElement(passwordField), password);
    }

    public void enterDisplayName(String name) {
        enterText(driver.findElement(displayNameField), name);
    }

    public void setDateOfBirth(String day, String monthValue, String year) {
        enterText(driver.findElement(dayField), day);
        // Note: For a real <select> dropdown, you would use Selenium's Select class.
        // Assuming Spotify uses a custom dropdown here, clicking and typing is often needed.
        enterText(driver.findElement(monthDropdown), monthValue);
        enterText(driver.findElement(yearField), year);
    }

    public void selectGender() {
        clickElement(driver.findElement(genderMaleRadio));
    }

    public void acceptTerms() {
        clickElement(driver.findElement(termsCheckbox));
    }

    public void clickSignUp() {
        clickElement(driver.findElement(submitButton));
    }

    public String getEmailErrorMessage() {
        return driver.findElement(emailErrorMessage).getText();
    }

    public boolean isErrorBannerDisplayed() {
        return driver.findElement(errorBanner).isDisplayed();
    }
}