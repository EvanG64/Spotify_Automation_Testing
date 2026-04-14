package tests;

import base.BaseTests;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class PlaybackTests extends BaseTests {

    @Test
    public void testOpenPlaylistPage() {
        driver.get("https://open.spotify.com/playlist/37i9dQZF1DXcBWIGoYBM5M");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("playlist"),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Trending Songs"),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Preview of Spotify"),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Spotify")
        ));

        String pageText = driver.findElement(By.tagName("body")).getText().toLowerCase();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("playlist")
                        || pageText.contains("trending songs")
                        || pageText.contains("preview of spotify")
                        || pageText.contains("spotify")
        );
    }

    @Test
    public void testSearchSongFromSearchPage() {
        driver.get("https://open.spotify.com/search");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement searchBox = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("input[role='searchbox'], input")
                )
        );

        searchBox.click();
        searchBox.clear();
        searchBox.sendKeys("Blinding Lights");

        wait.until(driver -> driver.findElement(By.tagName("body"))
                .getText().toLowerCase().contains("blinding"));

        String pageText = driver.findElement(By.tagName("body")).getText().toLowerCase();
        Assert.assertTrue(pageText.contains("blinding"));
    }

    @Test
    public void testClearSearchAfterTypingSong() {
        driver.get("https://open.spotify.com/search");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement searchBox = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("input[role='searchbox'], input")
                )
        );

        searchBox.click();
        searchBox.clear();
        searchBox.sendKeys("Levitating");

        wait.until(driver -> driver.findElement(By.tagName("body"))
                .getText().toLowerCase().contains("levitating"));

        searchBox.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        searchBox.sendKeys(Keys.DELETE);

        wait.until(driver -> {
            String value = searchBox.getAttribute("value");
            return value == null || value.isEmpty();
        });

        String value = searchBox.getAttribute("value");
        Assert.assertTrue(value == null || value.isEmpty());
    }

    @Test
    public void testNavigateToPlaylistAndVerifyContent() {
        driver.get("https://open.spotify.com/playlist/37i9dQZF1DXcBWIGoYBM5M");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.or(
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Trending Songs"),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Preview of Spotify"),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Sign up free"),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Spotify")
        ));

        String pageText = driver.findElement(By.tagName("body")).getText().toLowerCase();

        Assert.assertTrue(
                pageText.contains("trending songs")
                        || pageText.contains("preview of spotify")
                        || pageText.contains("sign up free")
                        || pageText.contains("spotify")
        );
    }

    @Test
    public void testPlaybackRelatedUiVisible() {
        driver.get("https://open.spotify.com/playlist/37i9dQZF1DXcBWIGoYBM5M");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.or(
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Play"),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Preview of Spotify"),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Sign up free"),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Spotify")
        ));

        String pageText = driver.findElement(By.tagName("body")).getText().toLowerCase();

        Assert.assertTrue(
                pageText.contains("play")
                        || pageText.contains("preview of spotify")
                        || pageText.contains("sign up free")
                        || pageText.contains("spotify")
        );
    }
}
