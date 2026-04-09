package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PlaybackPage {

    WebDriver driver;

    // Possible selectors for playback controls
    private final By playPauseButton = By.cssSelector("button[aria-label*='Play'], button[aria-label*='Pause']");
    private final By nextButton = By.cssSelector("button[aria-label*='Next']");
    private final By previousButton = By.cssSelector("button[aria-label*='Previous']");
    private final By shuffleButton = By.cssSelector("button[aria-label*='Shuffle']");
    private final By volumeBar = By.cssSelector("[data-testid='volume-bar'], input[type='range']");
    private final By nowPlayingTitle = By.cssSelector("[data-testid='context-item-info-title'], .encore-text-body-medium a");

    public PlaybackPage(WebDriver driver) {
        this.driver = driver;
    }

    public void togglePlayPause() {
        List<WebElement> buttons = driver.findElements(playPauseButton);
        if (!buttons.isEmpty() && buttons.get(0).isDisplayed()) {
            buttons.get(0).click();
        }
    }

    public void skipForward() {
        List<WebElement> buttons = driver.findElements(nextButton);
        if (!buttons.isEmpty() && buttons.get(0).isDisplayed()) {
            buttons.get(0).click();
        }
    }

    public void skipBackward() {
        List<WebElement> buttons = driver.findElements(previousButton);
        if (!buttons.isEmpty() && buttons.get(0).isDisplayed()) {
            buttons.get(0).click();
        }
    }

    public void toggleShuffle() {
        List<WebElement> buttons = driver.findElements(shuffleButton);
        if (!buttons.isEmpty() && buttons.get(0).isDisplayed()) {
            buttons.get(0).click();
        }
    }

    public boolean isVolumeVisible() {
        List<WebElement> controls = driver.findElements(volumeBar);
        return !controls.isEmpty() && controls.get(0).isDisplayed();
    }

    public String getNowPlayingTitle() {
        List<WebElement> titles = driver.findElements(nowPlayingTitle);
        if (!titles.isEmpty() && titles.get(0).isDisplayed()) {
            return titles.get(0).getText();
        }
        return "";
    }
}
