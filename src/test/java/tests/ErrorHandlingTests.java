package tests;

import base.BaseTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ErrorHandlingPage;

public class ErrorHandlingTests extends BaseTests {

    @Test
    public void test404PageNavigation() {
        driver.get("https://open.spotify.com/this-page-does-not-exist");
        ErrorHandlingPage errorPage = new ErrorHandlingPage(driver);
        Assert.assertTrue(errorPage.getErrorHeaderText().contains("Page not found"));
    }

    @Test
    public void testNavigationRecovery() {
        driver.get("https://open.spotify.com/404");
        ErrorHandlingPage errorPage = new ErrorHandlingPage(driver);
        errorPage.clickBackToHome();
        Assert.assertEquals(driver.getCurrentUrl(), "https://open.spotify.com/");
    }

    @Test
    public void testSpecialCharacterSearch() {
        var searchPage = homePage.navigateToSearch();
        searchPage.enterSearchQuery("';-- DROP TABLE users;");
        // Assert that the app handles SQL injection strings gracefully without crashing
        Assert.assertTrue(driver.getTitle().contains("Spotify"));
    }

    @Test
    public void testEmptyFormSubmissionError() {
        pages.LoginPage loginPage = homePage.clickLoginNav();
        loginPage.clickLogin();
        Assert.assertFalse(loginPage.getErrorMessage().isEmpty());
    }

    @Test
    public void testSessionTimeoutRedirection() {
        // Simulating navigating to a protected page while logged out
        driver.get("https://open.spotify.com/collection/tracks");
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
    }
}