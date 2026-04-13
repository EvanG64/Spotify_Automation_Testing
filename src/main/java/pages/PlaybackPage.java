package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class PlaybackPage {

    WebDriver driver;
    JavascriptExecutor js;

    // Possible selectors for playback controls
    private final By playPauseButton = By.cssSelector("button[aria-label*='Play'], button[aria-label*='Pause'], button[data-testid*='play'], button[data-testid*='pause'], button[data-testid*='control-button-playpause']");
    private final By nextButton = By.cssSelector("button[aria-label*='Next'], button[aria-label*='Skip'], button[data-testid*='next'], button[data-testid*='skip']");
    private final By previousButton = By.cssSelector("button[aria-label*='Previous'], button[aria-label*='Back'], button[data-testid*='previous'], button[data-testid*='back']");
    private final By shuffleButton = By.cssSelector("button[aria-label*='Shuffle'], button[data-testid*='shuffle'], button[data-testid*='control-button-shuffle']");
    private final By volumeBar = By.cssSelector("[data-testid='volume-bar']");
    private final By nowPlayingTitle = By.cssSelector("[data-testid='context-item-info-title'], .encore-text-body-medium a, [data-testid*='now-playing'], [data-testid*='track-title']");

    public PlaybackPage(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
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

    public void setVolume(int volumeLevel) {
        List<WebElement> volumeControls = driver.findElements(volumeBar);
        if (!volumeControls.isEmpty() && volumeControls.get(0).isDisplayed()) {
            WebElement volumeSlider = volumeControls.get(0);
            int width = volumeSlider.getSize().getWidth();
            int xOffset = (int) ((volumeLevel / 100.0) * width - width / 2.0);
            new Actions(driver)
                .moveToElement(volumeSlider, xOffset, 0)
                .click()
                .perform();
        }
    }

    public void setVolumeUpTo40() {
        setVolume(40);
    }

    public void setVolumeDownTo20() {
        setVolume(20);
    }

    public int getCurrentVolume() {
        List<WebElement> volumeControls = driver.findElements(volumeBar);
        if (!volumeControls.isEmpty() && volumeControls.get(0).isDisplayed()) {
            WebElement volumeSlider = volumeControls.get(0);
            String value = volumeSlider.getAttribute("aria-valuenow");
            if (value != null) {
                try {
                    return Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        }
        return 0;
    }

    public void ensurePlaybackStarted() {
        // Try to start playback if not already playing
        togglePlayPause();
        try {
            Thread.sleep(1000); // Wait for playback to start
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String getPlayPauseState() {
        List<WebElement> buttons = driver.findElements(playPauseButton);
        if (!buttons.isEmpty() && buttons.get(0).isDisplayed()) {
            String ariaLabel = buttons.get(0).getAttribute("aria-label");
            if (ariaLabel != null) {
                if (ariaLabel.contains("Pause")) return "Pause";
                if (ariaLabel.contains("Play")) return "Play";
            }
        }
        return "";
    }

    public boolean getShuffleState() {
        List<WebElement> buttons = driver.findElements(shuffleButton);
        if (!buttons.isEmpty() && buttons.get(0).isDisplayed()) {
            String ariaChecked = buttons.get(0).getAttribute("aria-checked");
            return "true".equals(ariaChecked);
        }
        return false;
    }
}