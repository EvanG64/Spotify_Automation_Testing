package tests;

import base.BaseTests;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.PlayListPage;

import java.time.Duration;
import java.util.List;

public class PlaylistTests extends BaseTests {

    private static final String PUBLIC_PLAYLIST =
            "https://open.spotify.com/playlist/37i9dQZF1DXcBWIGoYBM5M";

    // ── No login needed ──────────────────────────────────────────────────────

    @Test
    public void testPlaylistPageLoads() {
        driver.get(PUBLIC_PLAYLIST);
        PlayListPage playlistPage = new PlayListPage(driver);

        Assert.assertTrue(driver.getCurrentUrl().contains("/playlist/"));
        Assert.assertFalse(playlistPage.getPlaylistTitle().isEmpty(),
                "Playlist title should not be empty.");
    }

    @Test
    public void testPlaylistTitleVisible() {
        driver.get(PUBLIC_PLAYLIST);
        PlayListPage playlistPage = new PlayListPage(driver);

        Assert.assertFalse(playlistPage.getPlaylistTitle().isEmpty(),
                "Playlist title should be visible.");
    }

    @Test
    public void testPlaylistHasTracks() {
        driver.get(PUBLIC_PLAYLIST);
        PlayListPage playlistPage = new PlayListPage(driver);

        Assert.assertTrue(playlistPage.getTrackCount() > 0,
                "Playlist should have at least one track.");
    }

    @Test
    public void testPlaylistPlayButtonVisible() {
        driver.get(PUBLIC_PLAYLIST);
        PlayListPage playlistPage = new PlayListPage(driver);

        Assert.assertTrue(playlistPage.isPlayButtonVisible(),
                "Play button should be visible.");
    }

    @Test
    public void testPlaylistMoreOptionsVisible() {
        driver.get(PUBLIC_PLAYLIST);
        PlayListPage playlistPage = new PlayListPage(driver);

        Assert.assertTrue(playlistPage.isMoreOptionsVisible(),
                "More options button should be visible.");
    }

    // ── Login required — logs in ONCE, then all 3 tests reuse the session ────

    @Test(priority = 1)
    public void testSavePlaylistToLibrary() throws InterruptedException {
        loginAsTestUser(); // Login once here
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get(PUBLIC_PLAYLIST);
        wait.until(ExpectedConditions.urlContains("playlist"));
        Thread.sleep(2000);

        List<WebElement> saveButtons = driver.findElements(By.cssSelector(
                "button[aria-label*='Save'], button[aria-label*='Add'], " +
                        "button[aria-label*='Follow'], button[data-testid*='add']"
        ));

        if (saveButtons.isEmpty()) {
            saveButtons = driver.findElements(By.xpath(
                    "//*[contains(@aria-label,'Save') or contains(@aria-label,'Add to') " +
                            "or contains(@aria-label,'Follow')]"
            ));
        }

        Assert.assertFalse(saveButtons.isEmpty(),
                "Save/Follow button should be visible when logged in.");

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true); arguments[0].click();",
                saveButtons.get(0)
        );
        Thread.sleep(1000);

        Assert.assertTrue(
                driver.getCurrentUrl().contains("playlist"),
                "Should still be on playlist page after saving."
        );
    }

    @Test(priority = 2, dependsOnMethods = "testSavePlaylistToLibrary")
    public void testCreateNewPlaylist() throws InterruptedException {
        // Already logged in from testSavePlaylistToLibrary
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get("https://open.spotify.com/");
        wait.until(ExpectedConditions.urlContains("open.spotify.com"));

        // Wait for the "+ Create" button
        WebElement createBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.//span[normalize-space()='Create']] | " +
                        "//button[@aria-label='Create playlist or folder']")
        ));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true); arguments[0].click();", createBtn
        );

        // Click "Playlist" from the dropdown
        WebElement playlistOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[normalize-space()='Playlist'] | " +
                        "//*[contains(@class,'option') and normalize-space()='Playlist']")
        ));
        playlistOption.click();
        Thread.sleep(2000);

        String currentUrl = driver.getCurrentUrl();
        String pageSource = driver.getPageSource().toLowerCase();

        Assert.assertTrue(
                currentUrl.contains("playlist"),
                "Should be on the new playlist page. URL: " + currentUrl
        );
        Assert.assertTrue(
                pageSource.contains("my playlist") ||
                        pageSource.contains("playlist #") ||
                        pageSource.contains("add something") ||
                        pageSource.contains("find something") ||
                        pageSource.contains("playlist"),
                "New playlist page should show playlist content."
        );
    }

    @Test(priority = 3, dependsOnMethods = "testSavePlaylistToLibrary")
    public void testScrollThroughPlaylist() throws InterruptedException {
        // Already logged in from testSavePlaylistToLibrary
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get(PUBLIC_PLAYLIST);
        wait.until(ExpectedConditions.urlContains("playlist"));
        Thread.sleep(2000);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500)");
        Thread.sleep(500);
        js.executeScript("window.scrollBy(0, 500)");
        Thread.sleep(500);
        js.executeScript("window.scrollBy(0, 500)");
        Thread.sleep(500);
        js.executeScript("window.scrollTo(0, 0)");
        Thread.sleep(500);

        Assert.assertTrue(driver.getCurrentUrl().contains("playlist"),
                "Should still be on playlist page after scrolling.");
    }
}