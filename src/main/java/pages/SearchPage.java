package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchPage extends BasePage {
    private By searchInput = By.cssSelector("[data-testid='search-input']");
    private By clearSearchButton = By.cssSelector("[data-testid='clear-search']");
    private By firstResult = By.cssSelector("[data-testid='search-result-1']");
    private By podcastsFilter = By.xpath("//button[text()='Podcasts']");
    private By noResultsMessage = By.cssSelector(".no-results-message");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public void enterSearchQuery(String query) {
        enterText(driver.findElement(searchInput), query);
    }

    public void clearSearch() {
        clickElement(driver.findElement(clearSearchButton));
    }

    public void clickFirstResult() {
        clickElement(driver.findElement(firstResult));
    }

    public void filterByPodcasts() {
        clickElement(driver.findElement(podcastsFilter));
    }

    public boolean isNoResultsMessageDisplayed() {
        return driver.findElement(noResultsMessage).isDisplayed();
    }
}