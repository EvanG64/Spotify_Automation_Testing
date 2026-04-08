package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage extends BasePage {
    private By profileName = By.cssSelector("[data-testid='profile-name']");
    private By editProfileBtn = By.cssSelector("[data-testid='edit-profile-button']");
    private By followersCount = By.cssSelector("a[href$='/followers']");
    private By logoutBtn = By.cssSelector("[data-testid='user-widget-dropdown-logout']");
    private By displayNameInput = By.cssSelector("[data-testid='edit-profile-name-input']");
    private By saveProfileBtn = By.cssSelector("[data-testid='edit-profile-save']");

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public String getProfileName() {
        return driver.findElement(profileName).getText();
    }

    public void clickEditProfile() {
        clickElement(driver.findElement(editProfileBtn));
    }

    public void changeDisplayName(String newName) {
        enterText(driver.findElement(displayNameInput), newName);
        clickElement(driver.findElement(saveProfileBtn));
    }

    public void logout() {
        clickElement(driver.findElement(logoutBtn));
    }
}