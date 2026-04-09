package tests;

import base.BaseTests;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProfileSettingsTests extends BaseTests {

    @Test
    public void testLoginPageLoads() {
        driver.get("https://accounts.spotify.com/en/login");

        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
        Assert.assertTrue(driver.getTitle().toLowerCase().contains("spotify"));
    }

    @Test
    public void testSignupPageLoads() {
        driver.get("https://www.spotify.com/us/signup");

        Assert.assertTrue(driver.getCurrentUrl().toLowerCase().contains("signup"));
    }

    @Test
    public void testPasswordResetPageLoads() {
        driver.get("https://www.spotify.com/us/password-reset/");

        Assert.assertTrue(driver.getCurrentUrl().toLowerCase().contains("password"));
    }

    @Test
    public void testAccountHelpPageLoads() {
        driver.get("https://support.spotify.com/us/category/account-help/");

        Assert.assertTrue(driver.getCurrentUrl().toLowerCase().contains("account"));
    }

    @Test
    public void testSpotifyAccountPagesContainBranding() {
        driver.get("https://accounts.spotify.com/en/login");

        String pageText = driver.findElement(org.openqa.selenium.By.tagName("body")).getText().toLowerCase();

        Assert.assertTrue(
                pageText.contains("spotify")
                        || pageText.contains("welcome back")
                        || pageText.contains("log in")
        );
    }
}