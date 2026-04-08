package tests;

import base.BaseTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SearchPage;

public class SearchTests extends BaseTests {

    @Test
    public void testSearchValidSong() {
        SearchPage searchPage = homePage.navigateToSearch();
        searchPage.enterSearchQuery("Bohemian Rhapsody");
        // Assert results are present
    }

    @Test
    public void testSearchValidArtist() {
        SearchPage searchPage = homePage.navigateToSearch();
        searchPage.enterSearchQuery("Queen");
        // Assert artist profile is returned
    }

    @Test
    public void testSearchInvalidQuery() {
        SearchPage searchPage = homePage.navigateToSearch();
        searchPage.enterSearchQuery("asdfghjkl123456789");
        Assert.assertTrue(searchPage.isNoResultsMessageDisplayed());
    }

    @Test
    public void testClearSearchFunctionality() {
        SearchPage searchPage = homePage.navigateToSearch();
        searchPage.enterSearchQuery("Beatles");
        searchPage.clearSearch();
        // Assert input field is empty
    }

    @Test
    public void testPodcastFilter() {
        SearchPage searchPage = homePage.navigateToSearch();
        searchPage.enterSearchQuery("Tech News");
        searchPage.filterByPodcasts();
        // Assert podcast specific UI elements appear
    }
}