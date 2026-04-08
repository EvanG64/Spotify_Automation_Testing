package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PlaybackPage extends BasePage {
    private By playPauseButton = By.cssSelector("[data-testid='control-button-playpause']");
    private By skipForwardButton = By.cssSelector("[data-testid='control-button-skip-forward']");
    private By skipBackButton = By.cssSelector("[data-testid='control-button-skip-back']");
    private By shuffleButton = By.cssSelector("[data-testid='control-button-shuffle']");
    private By repeatButton = By.cssSelector("[data-testid='control-button-repeat']");
    private By nowPlayingTitle = By.cssSelector("[data-testid='now-playing-widget']");

    public PlaybackPage(WebDriver driver) {
        super(driver);
    }

    public void togglePlayPause() {
        clickElement(driver.findElement(playPauseButton));
    }

    public void skipForward() {
        clickElement(driver.findElement(skipForwardButton));
    }

    public void skipBackward() {
        clickElement(driver.findElement(skipBackButton));
    }

    public void toggleShuffle() {
        clickElement(driver.findElement(shuffleButton));
    }

    public String getNowPlayingTitle() {
        return driver.findElement(nowPlayingTitle).getText();
    }
}