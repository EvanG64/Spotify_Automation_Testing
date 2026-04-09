package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ErrorHandlingPage {

    WebDriver driver;

    public ErrorHandlingPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getErrorHeaderText() {
        return driver.findElement(By.tagName("h1")).getText();
    }
}
