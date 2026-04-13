package tests;

import base.BaseTests;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTests extends BaseTests {

    @Test
    public void testSearchValidSong() {
        driver.get("https://open.spotify.com/search");
        pages.SearchPage searchPage = new pages.SearchPage(driver);

        searchPage.enterSearchQuery("Bohemian Rhapsody");

        Assert.assertFalse(searchPage.isNoResultsMessageDisplayed(), "Search for valid song should show results.");
    }

    @Test
    public void testSearchValidArtist() {
        driver.get("https://open.spotify.com/search");
        pages.SearchPage searchPage = new pages.SearchPage(driver);

        searchPage.enterSearchQuery("Queen");

        Assert.assertFalse(searchPage.isNoResultsMessageDisplayed(), "Search for valid artist should show results.");
    }

    @Test
    public void testSearchInvalidQuery() {
        driver.get("https://open.spotify.com/search");
        pages.SearchPage searchPage = new pages.SearchPage(driver);

        searchPage.enterSearchQuery("asdfghjklqwertyuiop");

        Assert.assertTrue(searchPage.isNoResultsMessageDisplayed(), "Search for invalid query should show no results.");
    }

    @Test
    public void testClearSearchFunctionality() {
        driver.get("https://open.spotify.com/search");
        pages.SearchPage searchPage = new pages.SearchPage(driver);

        searchPage.enterSearchQuery("test");
        searchPage.clearSearch();

        Assert.assertTrue(searchPage.isSearchEmpty(), "Search should be cleared.");
    }

    @Test
    public void testPodcastFilter() {
        driver.get("https://open.spotify.com/search");
        pages.SearchPage searchPage = new pages.SearchPage(driver);

        searchPage.enterSearchQuery("podcast");
        searchPage.filterByPodcasts();

        Assert.assertFalse(searchPage.isNoResultsMessageDisplayed(), "Filtering by podcasts should show results.");
    }
}