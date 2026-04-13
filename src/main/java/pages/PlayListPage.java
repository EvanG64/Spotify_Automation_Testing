package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PlayListPage {

    WebDriver driver;

    private final By playlistTitle = By.tagName("h1");
    private final By trackRows = By.cssSelector("[data-testid='tracklist-row']");

    // broader play button selectors
    private final By playButtons = By.cssSelector(
            "button[data-testid='play-button'], " +
                    "button[aria-label*='Play'], " +
                    "button[aria-label*='play']"
    );

    // broader more options selectors
    private final By moreOptionsButtons = By.cssSelector(
            "button[aria-label*='more'], " +
                    "button[aria-label*='More']"
    );

    public PlayListPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getPlaylistTitle() {
        List<WebElement> titles = driver.findElements(playlistTitle);
        if (!titles.isEmpty() && titles.get(0).isDisplayed()) {
            return titles.get(0).getText();
        }
        return "";
    }

    public int getTrackCount() {
        return driver.findElements(trackRows).size();
    }

    public boolean isPlayButtonVisible() {
        List<WebElement> buttons = driver.findElements(playButtons);
        for (WebElement button : buttons) {
            if (button.isDisplayed()) {
                return true;
            }
        }
        return false;
    }

    public boolean isMoreOptionsVisible() {
        List<WebElement> buttons = driver.findElements(moreOptionsButtons);
        for (WebElement button : buttons) {
            if (button.isDisplayed()) {
                return true;
            }
        }
        return false;
    }

    public void clickPlay() {
        List<WebElement> buttons = driver.findElements(playButtons);
        for (WebElement button : buttons) {
            if (button.isDisplayed()) {
                button.click();
                break;
            }
        }
    }
}