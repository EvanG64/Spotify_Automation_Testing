package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchPage extends BasePage {

    private By searchInput = By.cssSelector("input");
    private By podcastsFilter = By.xpath("//button[contains(.,'Podcasts') or contains(.,'Podcast')]");
    private By noResultsMessage = By.xpath("//*[contains(text(),'No results') or contains(text(),'Try searching') or contains(text(),'didn’t match any results')]");
    private By bodyText = By.tagName("body");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public void enterSearchQuery(String query) {
        List<WebElement> inputs = driver.findElements(searchInput);
        if (!inputs.isEmpty()) {
            WebElement input = inputs.get(0);
            input.click();
            input.clear();
            input.sendKeys(query);
            input.sendKeys(Keys.ENTER);
        }
    }

    public void clearSearch() {
        List<WebElement> inputs = driver.findElements(searchInput);
        if (!inputs.isEmpty()) {
            WebElement input = inputs.get(0);
            input.click();
            input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            input.sendKeys(Keys.DELETE);
        }
    }

    public boolean isSearchEmpty() {
        List<WebElement> inputs = driver.findElements(searchInput);
        if (!inputs.isEmpty()) {
            String value = inputs.get(0).getAttribute("value");
            return value == null || value.isEmpty();
        }
        return false;
    }

    public void filterByPodcasts() {
        List<WebElement> filters = driver.findElements(podcastsFilter);
        if (!filters.isEmpty() && filters.get(0).isDisplayed()) {
            filters.get(0).click();
        }
    }

    public boolean isNoResultsMessageDisplayed() {
        List<WebElement> messages = driver.findElements(noResultsMessage);
        return !messages.isEmpty() && messages.get(0).isDisplayed();
    }

    public String getPageText() {
        return driver.findElement(bodyText).getText();
    }
}
