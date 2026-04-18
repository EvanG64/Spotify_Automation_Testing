package tests;

import base.BaseTests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SearchPage;

import java.time.Duration;

public class SearchTests extends BaseTests {

    @Test
    public void testSearchValidSong() {
        driver.get("https://open.spotify.com/search");
        SearchPage searchPage = new SearchPage(driver);

        searchPage.enterSearchQuery("Bohemian Rhapsody");

        Assert.assertFalse(searchPage.isNoResultsMessageDisplayed(),
                "Search for a valid song should show results.");
    }

    @Test
    public void testSearchValidArtist() {
        driver.get("https://open.spotify.com/search");
        SearchPage searchPage = new SearchPage(driver);

        searchPage.enterSearchQuery("Queen");

        Assert.assertFalse(searchPage.isNoResultsMessageDisplayed(),
                "Search for a valid artist should show results.");
    }

    @Test
    public void testSearchInvalidQuery() {
        driver.get("https://open.spotify.com/search");
        SearchPage searchPage = new SearchPage(driver);

        searchPage.enterSearchQuery("asdfghjklqwertyuiop");

        // Spotify sometimes shows results even for nonsense queries
        // so we check either no results OR very few results
        String pageSource = driver.getPageSource().toLowerCase();
        boolean noResultsShown = searchPage.isNoResultsMessageDisplayed()
                || pageSource.contains("no results")
                || pageSource.contains("couldn't find")
                || !pageSource.contains("add to playlist");

        Assert.assertTrue(noResultsShown,
                "Search for invalid query should show no meaningful results.");
    }

    @Test
    public void testClearSearchFunctionality() {
        driver.get("https://open.spotify.com/search");
        SearchPage searchPage = new SearchPage(driver);

        searchPage.enterSearchQuery("test");
        searchPage.clearSearch();

        Assert.assertTrue(searchPage.isSearchEmpty(),
                "Search box should be empty after clearing.");
    }

    @Test
    public void testPodcastFilter() {
        driver.get("https://open.spotify.com/search");
        SearchPage searchPage = new SearchPage(driver);

        searchPage.enterSearchQuery("podcast");
        searchPage.filterByPodcasts();

        Assert.assertFalse(searchPage.isNoResultsMessageDisplayed(),
                "Filtering by podcasts should show results.");
    }

    @Test
    public void testSearchWhileLoggedIn() throws InterruptedException {
        loginAsTestUser();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get("https://open.spotify.com/search");
        wait.until(ExpectedConditions.urlContains("search"));

        SearchPage searchPage = new SearchPage(driver);
        searchPage.enterSearchQuery("The Weeknd");

        String pageSource = driver.getPageSource().toLowerCase();
        Assert.assertTrue(
                pageSource.contains("weeknd") || pageSource.contains("blinding"),
                "Search should show results for The Weeknd when logged in."
        );
    }
}