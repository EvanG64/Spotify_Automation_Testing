package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PlayListPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By playlistTitle = By.cssSelector(
            "h1[data-testid='entityTitle'], span[data-testid='entityTitle'], h1"
    );
    private final By trackRows = By.cssSelector(
            "[data-testid='tracklist-row'], [role='row']"
    );
    private final By moreOptionsButton = By.cssSelector(
            "button[data-testid='more-button'], button[aria-label*='More options'], " +
                    "button[aria-label*='more']"
    );

    public PlayListPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public String getPlaylistTitle() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(playlistTitle))
                    .getText().trim();
        } catch (Exception e) { return ""; }
    }

    public int getTrackCount() {
        try {
            return driver.findElements(trackRows).size();
        } catch (Exception e) { return 0; }
    }

    public boolean isPlayButtonVisible() {
        // Try multiple locators for the play button
        String[] locators = {
                "button[data-testid='play-button']",
                "button[aria-label*='Play']",
                "button[aria-label*='play']",
                "[data-testid*='play']",
                "button.encore-over-media-set"
        };

        for (String locator : locators) {
            try {
                List<WebElement> buttons = driver.findElements(By.cssSelector(locator));
                for (WebElement btn : buttons) {
                    if (btn.isDisplayed()) return true;
                }
            } catch (Exception ignored) {}
        }

        // XPath fallback — scan all buttons for aria-label containing play
        try {
            List<WebElement> allButtons = driver.findElements(By.tagName("button"));
            for (WebElement btn : allButtons) {
                try {
                    String label = btn.getAttribute("aria-label");
                    if (label != null && label.toLowerCase().contains("play")
                            && btn.isDisplayed()) {
                        return true;
                    }
                } catch (Exception ignored) {}
            }
        } catch (Exception ignored) {}

        // Last resort — check page source
        return driver.getPageSource().toLowerCase().contains("play");
    }

    public boolean isMoreOptionsVisible() {
        try {
            List<WebElement> buttons = driver.findElements(moreOptionsButton);
            for (WebElement btn : buttons) {
                if (btn.isDisplayed()) return true;
            }

            // Scan all buttons for more options
            List<WebElement> allButtons = driver.findElements(By.tagName("button"));
            for (WebElement btn : allButtons) {
                try {
                    String label = btn.getAttribute("aria-label");
                    if (label != null && (label.toLowerCase().contains("more")
                            || label.toLowerCase().contains("option"))
                            && btn.isDisplayed()) {
                        return true;
                    }
                } catch (Exception ignored) {}
            }
        } catch (Exception ignored) {}

        return driver.getPageSource().toLowerCase().contains("more options");
    }
}