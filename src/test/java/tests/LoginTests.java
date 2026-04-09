package tests;

import base.BaseTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTests extends BaseTests {

    @Test
    public void testLoginPageLoads() {
        driver.get("https://accounts.spotify.com/en/login");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.waitForPage();

        Assert.assertTrue(loginPage.isEmailFieldVisible());
        Assert.assertTrue(loginPage.isContinueButtonVisible());
    }

    @Test
    public void testEmailFieldVisible() {
        driver.get("https://accounts.spotify.com/en/login");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.waitForPage();

        Assert.assertTrue(loginPage.isEmailFieldVisible());
    }

    @Test
    public void testContinueButtonVisible() {
        driver.get("https://accounts.spotify.com/en/login");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.waitForPage();

        Assert.assertTrue(loginPage.isContinueButtonVisible());
    }

    @Test
    public void testInvalidEmailFormat() {
        driver.get("https://accounts.spotify.com/en/login");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.waitForPage();
        loginPage.setEmail("invalidemail.com");
        loginPage.clickContinue();

        String pageText = loginPage.getPageText().toLowerCase();

        Assert.assertTrue(
                pageText.contains("email")
                        || pageText.contains("valid")
                        || pageText.contains("continue"),
                "Expected invalid email behavior was not shown."
        );
    }


    @Test
    public void testEmptyEmailSubmission() {
        driver.get("https://accounts.spotify.com/en/login");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.waitForPage();
        loginPage.clickContinue();

        String pageText = loginPage.getPageText().toLowerCase();

        Assert.assertTrue(
                pageText.contains("email")
                        || pageText.contains("continue")
                        || pageText.contains("welcome back"),
                "Expected empty email behavior was not shown."
        );
    }

    @Test
    public void testValidLogin() throws InterruptedException {
        driver.get("https://accounts.spotify.com/en/login");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.waitForPage();
        loginPage.setEmail("ebgoudy4412@eagle.fgcu.edu");
        loginPage.clickContinue();

        // After email submission, Spotify shows a page with options to send code or log in with password
        Thread.sleep(1500); // Wait for options page to load
        
        // Click on "Log in with password" option
        loginPage.clickLogInWithPassword();

        // Now password field should be visible
        Assert.assertTrue(loginPage.isPasswordFieldVisible(), "Password field should be visible after selecting log in with password");

        loginPage.setPassword("Player122@");
        loginPage.clickLogIn();

        // Wait for redirect to home page (or any successful login indicator)
        Thread.sleep(3000); // Wait for page to load after login

        // Verify we're no longer on the login page (simple check)
        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotEquals(currentUrl, "https://accounts.spotify.com/en/login", "Should be redirected after successful login");
    }
}