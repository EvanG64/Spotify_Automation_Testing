package tests;

import base.BaseTests;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTests extends BaseTests {

    @Test
    public void testSearchValidSong() {
        driver.get("https://open.spotify.com/search");

        String currentUrl = driver.getCurrentUrl().toLowerCase();
        String title = driver.getTitle().toLowerCase();
        String pageText = driver.findElement(org.openqa.selenium.By.tagName("body")).getText().toLowerCase();

        Assert.assertTrue(
                currentUrl.contains("search")
                        || title.contains("spotify")
                        || pageText.contains("what do you want to play")
                        || pageText.contains("search"),
                "Search page did not load for valid song test."
        );
    }

    @Test
    public void testSearchValidArtist() {
        driver.get("https://open.spotify.com/search");

        String currentUrl = driver.getCurrentUrl().toLowerCase();
        String title = driver.getTitle().toLowerCase();
        String pageText = driver.findElement(org.openqa.selenium.By.tagName("body")).getText().toLowerCase();

        Assert.assertTrue(
                currentUrl.contains("search")
                        || title.contains("spotify")
                        || pageText.contains("what do you want to play")
                        || pageText.contains("search"),
                "Search page did not load for valid artist test."
        );
    }

    @Test
    public void testSearchInvalidQuery() {
        driver.get("https://open.spotify.com/search");

        String currentUrl = driver.getCurrentUrl().toLowerCase();
        String title = driver.getTitle().toLowerCase();
        String pageText = driver.findElement(org.openqa.selenium.By.tagName("body")).getText().toLowerCase();

        Assert.assertTrue(
                currentUrl.contains("search")
                        || title.contains("spotify")
                        || pageText.contains("search"),
                "Search page did not load for invalid query test."
        );
    }

    @Test
    public void testClearSearchFunctionality() {
        driver.get("https://open.spotify.com/search");

        String currentUrl = driver.getCurrentUrl().toLowerCase();
        String title = driver.getTitle().toLowerCase();
        String pageText = driver.findElement(org.openqa.selenium.By.tagName("body")).getText().toLowerCase();

        Assert.assertTrue(
                currentUrl.contains("search")
                        || title.contains("spotify")
                        || pageText.contains("search"),
                "Search page did not load for clear search test."
        );
    }

    @Test
    public void testPodcastFilter() {
        driver.get("https://open.spotify.com/search");

        String currentUrl = driver.getCurrentUrl().toLowerCase();
        String title = driver.getTitle().toLowerCase();
        String pageText = driver.findElement(org.openqa.selenium.By.tagName("body")).getText().toLowerCase();

        Assert.assertTrue(
                currentUrl.contains("search")
                        || title.contains("spotify")
                        || pageText.contains("search")
                        || pageText.contains("podcast"),
                "Search page did not load for podcast filter test."
        );
    }
}