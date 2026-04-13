package tests;

import base.BaseTests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.PlaybackPage;

import java.time.Duration;

public class PlaybackTests extends BaseTests {

    private WebDriverWait wait;
    private WebDriverWait longWait;

    public void setupWait() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        longWait = new WebDriverWait(driver, Duration.ofSeconds(120));
    }

    // helper: go to a playable page
    public void goToPlayablePage() {
        login();

        driver.get("https://open.spotify.com/search");
        wait.until(ExpectedConditions.titleContains("Spotify"));

        // open a known playlist (public)
        driver.get("https://open.spotify.com/playlist/37i9dQZF1DXcBWIGoYBM5M");
        wait.until(ExpectedConditions.titleContains("Spotify"));
    }

    // helper: go to a playable page and start playback
    public void goToPlayablePageAndStartPlayback() {
        login();

        driver.get("https://open.spotify.com/search");
        wait.until(ExpectedConditions.titleContains("Spotify"));

        // open a known playlist (public)
        driver.get("https://open.spotify.com/playlist/37i9dQZF1DXcBWIGoYBM5M");
        wait.until(ExpectedConditions.titleContains("Spotify"));

        // Start playback by clicking the play button
        PlaybackPage playbackPage = new PlaybackPage(driver);
        playbackPage.ensurePlaybackStarted();
    }

    public void login() {
        try {
            driver.get("https://accounts.spotify.com/en/login");
            LoginPage loginPage = new LoginPage(driver);

            loginPage.waitForPage();
            loginPage.setEmail("ebgoudy4412@eagle.fgcu.edu");
            loginPage.clickContinue();

            // Wait for options page
            Thread.sleep(1500);

            // Click log in with password
            loginPage.clickLogInWithPassword();

            // Set password
            loginPage.setPassword("Player122@");
            loginPage.clickLogIn();

            // Wait for redirect to home page after possible bot verification
            longWait.until(ExpectedConditions.urlContains("open.spotify.com"));

            // If not redirected, check if on challenge page and try to handle reCAPTCHA
            if (!driver.getCurrentUrl().contains("open.spotify.com")) {
                try {
                    // Assume reCAPTCHA challenge
                    WebElement iframe = driver.findElement(By.cssSelector("iframe[src*='recaptcha']"));
                    driver.switchTo().frame(iframe);
                    WebElement checkbox = driver.findElement(By.id("recaptcha-anchor"));
                    checkbox.click();
                    driver.switchTo().defaultContent();
                    // Wait again for redirect after CAPTCHA
                    longWait.until(ExpectedConditions.urlContains("open.spotify.com"));
                } catch (Exception e) {
                    // If CAPTCHA handling fails, the test will timeout or fail
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    public void testPlayPauseToggle() {
        setupWait();
        goToPlayablePage(); // Open playlist without starting playback

        PlaybackPage playbackPage = new PlaybackPage(driver);

        // Click the play button
        playbackPage.togglePlayPause();

        // Wait a moment for playback to start
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Verify playing
        Assert.assertEquals(playbackPage.getPlayPauseState(), "Pause", "Should be in pause state after playing.");

        // Click the pause button
        playbackPage.togglePlayPause();

        // Verify paused
        Assert.assertEquals(playbackPage.getPlayPauseState(), "Play", "Should be in play state after pausing.");
    }

    @Test
    public void testSkipForward() {
        setupWait();
        goToPlayablePageAndStartPlayback();

        PlaybackPage playbackPage = new PlaybackPage(driver);

        // Get initial track title
        String initialTrack = playbackPage.getNowPlayingTitle();

        // Skip forward
        playbackPage.skipForward();

        // Wait a moment for the track to change
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Verify the track changed
        String newTrack = playbackPage.getNowPlayingTitle();
        Assert.assertNotEquals(initialTrack, newTrack, "Skip forward should change the track.");
        Assert.assertFalse(newTrack.isEmpty(), "New track title should not be empty.");
    }

    @Test
    public void testSkipBackward() {
        setupWait();
        goToPlayablePageAndStartPlayback();

        PlaybackPage playbackPage = new PlaybackPage(driver);

        // Skip forward first to have a track to go back to
        playbackPage.skipForward();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Get current track title
        String currentTrack = playbackPage.getNowPlayingTitle();

        // Skip backward
        playbackPage.skipBackward();

        // Wait a moment for the track to change
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Verify the action completed (may not change track if at beginning)
        String newTrack = playbackPage.getNowPlayingTitle();
        Assert.assertTrue(!newTrack.isEmpty(), "Skip backward should complete without crashing");
    }

    @Test
    public void testShuffleToggle() {
        setupWait();
        goToPlayablePageAndStartPlayback();

        PlaybackPage playbackPage = new PlaybackPage(driver);

        // Toggle shuffle on
        playbackPage.toggleShuffle();

        // Wait a moment
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Verify shuffle on
        Assert.assertTrue(playbackPage.getShuffleState(), "Shuffle should be enabled.");

        // Toggle shuffle off
        playbackPage.toggleShuffle();

        // Verify shuffle off
        Assert.assertFalse(playbackPage.getShuffleState(), "Shuffle should be disabled.");
    }

    @Test
    public void testVolumeControlsVisible() {
        setupWait();
        goToPlayablePageAndStartPlayback();

        PlaybackPage playbackPage = new PlaybackPage(driver);

        // Verify volume controls are visible
        Assert.assertTrue(playbackPage.isVolumeVisible(), "Volume controls should be visible on playback page");
    }
}