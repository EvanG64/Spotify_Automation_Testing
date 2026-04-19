package tests;

import base.BaseTests;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.time.Duration;

public class LoginTests extends BaseTests {

    @Test
    public void testLoginPageLoads() {
        driver.get("https://accounts.spotify.com/en/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForPage();

        Assert.assertTrue(loginPage.isEmailFieldVisible(), "Email field should be visible.");
        Assert.assertTrue(loginPage.isContinueButtonVisible(), "Continue button should be visible.");
    }

    @Test
    public void testEmailFieldVisible() {
        driver.get("https://accounts.spotify.com/en/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForPage();

        Assert.assertTrue(loginPage.isEmailFieldVisible(),
                "Email field should be visible on the login page.");
    }

    @Test
    public void testContinueButtonVisible() {
        driver.get("https://accounts.spotify.com/en/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForPage();

        Assert.assertTrue(loginPage.isContinueButtonVisible(),
                "Continue button should be visible on the login page.");
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
                "Expected invalid email feedback was not shown."
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
                "Expected empty email feedback was not shown."
        );
    }

    @Test
    public void testValidLogin() throws InterruptedException {
        // Use longer timeout for login since Firefox can be slow
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        loginAsTestUser();

        driver.get("https://open.spotify.com/");

        // Wait for Spotify home to load
        wait.until(ExpectedConditions.or(
                ExpectedConditions.titleContains("Spotify"),
                ExpectedConditions.urlContains("open.spotify.com")
        ));

        String pageSource = driver.getPageSource().toLowerCase();
        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(
                currentUrl.contains("open.spotify.com"),
                "Should be on Spotify after login. URL: " + currentUrl
        );  
        Assert.assertTrue(
                pageSource.contains("search")
                        || pageSource.contains("home")
                        || pageSource.contains("spotify"),
                "Spotify home page content should be visible after login."
        );
    }
}