package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private By loginButton = By.cssSelector("[data-testid='login-button']");
    private By searchNav = By.cssSelector("a[href='/search']");
    private By profileMenu = By.cssSelector("[data-testid='user-widget-link']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public LoginPage clickLoginNav() {
        clickElement(driver.findElement(loginButton));
        return new LoginPage(driver);
    }

    public SearchPage navigateToSearch() {
        clickElement(driver.findElement(searchNav));
        return new SearchPage(driver);
    }

    public ProfilePage openProfileMenu() {
        clickElement(driver.findElement(profileMenu));
        return new ProfilePage(driver);
    }
}