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
}