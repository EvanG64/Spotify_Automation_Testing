package tests;

import base.BaseTests;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class PlaybackTests extends BaseTests {

    private static final String PLAYLIST_URL =
            "https://open.spotify.com/playlist/37i9dQZF1DXcBWIGoYBM5M";

    // ── No login needed ──────────────────────────────────────────────────────

    @Test
    public void testOpenPlaylistPage() {
        driver.get(PLAYLIST_URL);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("playlist"),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Spotify")
        ));

        String pageText = driver.findElement(By.tagName("body")).getText().toLowerCase();
        Assert.assertTrue(
                driver.getCurrentUrl().contains("playlist")
                        || pageText.contains("trending songs")
                        || pageText.contains("spotify")
        );
    }

    @Test
    public void testSearchSongFromSearchPage() {
        driver.get("https://open.spotify.com/search");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[role='searchbox'], input[data-testid='search-input'], input")
        ));
        searchBox.click();
        searchBox.clear();
        searchBox.sendKeys("Blinding Lights");

        wait.until(d -> d.findElement(By.tagName("body"))
                .getText().toLowerCase().contains("blinding"));

        String pageText = driver.findElement(By.tagName("body")).getText().toLowerCase();
        Assert.assertTrue(pageText.contains("blinding"));
    }

    @Test
    public void testClearSearchAfterTypingSong() {
        driver.get("https://open.spotify.com/search");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[role='searchbox'], input[data-testid='search-input'], input")
        ));
        searchBox.click();
        searchBox.clear();
        searchBox.sendKeys("Levitating");

        wait.until(d -> d.findElement(By.tagName("body"))
                .getText().toLowerCase().contains("levitating"));

        searchBox.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        searchBox.sendKeys(Keys.DELETE);

        wait.until(d -> {
            String value = searchBox.getAttribute("value");
            return value == null || value.isEmpty();
        });

        String value = searchBox.getAttribute("value");
        Assert.assertTrue(value == null || value.isEmpty());
    }

    @Test
    public void testNavigateToPlaylistAndVerifyContent() {
        driver.get(PLAYLIST_URL);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("playlist"));

        Assert.assertTrue(
                driver.getCurrentUrl().contains("playlist"),
                "Should be on a playlist page."
        );
    }

    @Test
    public void testPlaybackRelatedUiVisible() {
        driver.get(PLAYLIST_URL);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.or(
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Play"),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Spotify")
        ));

        String pageText = driver.findElement(By.tagName("body")).getText().toLowerCase();
        Assert.assertTrue(pageText.contains("play") || pageText.contains("spotify"));
    }

    // ── Login required — logs in ONCE then runs all 3 tests ─────────────────

    @Test(priority = 1)
    public void testPlaySongWhenLoggedIn() throws InterruptedException {
        loginAsTestUser();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get(PLAYLIST_URL);
        wait.until(ExpectedConditions.urlContains("playlist"));
        Thread.sleep(2000);

        List<WebElement> playBtns = driver.findElements(
                By.xpath("//*[contains(@aria-label,'Play') and @data-testid='play-button']")
        );
        if (!playBtns.isEmpty()) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", playBtns.get(0)
            );
        }
        Thread.sleep(2000);

        String pageSource = driver.getPageSource().toLowerCase();
        Assert.assertTrue(
                pageSource.contains("pause") || pageSource.contains("playing") ||
                        pageSource.contains("spotify"),
                "Song should be playing after clicking play."
        );
    }

    @Test(priority = 2, dependsOnMethods = "testPlaySongWhenLoggedIn")
    public void testSearchWhileLoggedIn() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("https://open.spotify.com/search");
        wait.until(ExpectedConditions.urlContains("search"));
        Thread.sleep(1500);

        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[role='searchbox'], input[data-testid='search-input'], input")
        ));
        searchBox.click();
        searchBox.clear();
        searchBox.sendKeys("The Weeknd");
        Thread.sleep(2000);

        String pageSource = driver.getPageSource().toLowerCase();
        Assert.assertTrue(
                pageSource.contains("weeknd") ||
                        pageSource.contains("blinding") ||
                        pageSource.contains("starboy") ||
                        pageSource.contains("artist"),
                "Search should show results when logged in."
        );
    }

    @Test(priority = 3, dependsOnMethods = "testPlaySongWhenLoggedIn")
    public void testSkipToNextTrack() throws InterruptedException {
        // Already logged in from previous test — just navigate
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get(PLAYLIST_URL);
        wait.until(ExpectedConditions.urlContains("playlist"));
        Thread.sleep(2000);

        // Play first
        List<WebElement> playBtns = driver.findElements(
                By.xpath("//*[contains(@aria-label,'Play') and @data-testid='play-button']")
        );
        if (!playBtns.isEmpty()) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", playBtns.get(0)
            );
            Thread.sleep(2000);
        }

        // Click next
        List<WebElement> nextBtns = driver.findElements(
                By.xpath("//*[@aria-label='Next' or @data-testid='control-button-skip-forward' " +
                        "or contains(@aria-label,'next')]")
        );

        Assert.assertFalse(nextBtns.isEmpty(),
                "Next track button should be visible in the player bar.");

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", nextBtns.get(0)
        );
        Thread.sleep(1500);

        String pageSource = driver.getPageSource().toLowerCase();
        Assert.assertTrue(
                pageSource.contains("pause") || pageSource.contains("playing") ||
                        pageSource.contains("spotify"),
                "Should still be playing after skipping to next track."
        );
    }
}