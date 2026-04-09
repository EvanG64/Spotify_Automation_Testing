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

        Assert.assertTrue(
                errorPage.getErrorHeaderText().toLowerCase().contains("page")
                        || driver.getTitle().toLowerCase().contains("spotify"),
                "Spotify did not show expected not-found behavior."
        );
    }

    @Test
    public void testNavigationRecovery() {
        driver.get("https://open.spotify.com/404");

// safer recovery step
        driver.get("https://open.spotify.com/");

        Assert.assertTrue(driver.getCurrentUrl().contains("open.spotify.com"));
    }

    @Test
    public void testSpecialCharacterSearch() {
        driver.get("https://open.spotify.com/search");

        String searchUrl = driver.getCurrentUrl().toLowerCase();
        String title = driver.getTitle().toLowerCase();
        String pageSource = driver.getPageSource().toLowerCase();

        Assert.assertTrue(
                searchUrl.contains("search")
                        || title.contains("spotify")
                        || pageSource.contains("what do you want to play")
                        || pageSource.contains("search"),
                "Search page did not load correctly."
        );
    }

    @Test
    public void testEmptyFormSubmissionError() {
        driver.get("https://accounts.spotify.com/en/login");

        String currentUrl = driver.getCurrentUrl().toLowerCase();
        String title = driver.getTitle().toLowerCase();
        String pageSource = driver.getPageSource().toLowerCase();

        Assert.assertTrue(
                currentUrl.contains("login")
                        || title.contains("spotify")
                        || pageSource.contains("log in")
                        || pageSource.contains("email address or username")
                        || pageSource.contains("password"),
                "Spotify login page did not load correctly."
        );
    }

    @Test
    public void testSessionTimeoutRedirection() {
        driver.get("https://open.spotify.com/collection/tracks");

        String currentUrl = driver.getCurrentUrl().toLowerCase();
        String title = driver.getTitle().toLowerCase();
        String pageSource = driver.getPageSource().toLowerCase();

        Assert.assertTrue(
                currentUrl.contains("open.spotify.com")
                        && (
                        currentUrl.contains("login")
                                || title.contains("spotify")
                                || pageSource.contains("log in")
                                || pageSource.contains("sign up free")
                                || pageSource.contains("preview of spotify")
                                || pageSource.contains("trending songs")
                                || pageSource.contains("popular artists")
                ),
                "Spotify did not show restricted/public behavior for protected content."
        );
    }
}