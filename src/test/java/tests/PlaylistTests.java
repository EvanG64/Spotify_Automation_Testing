package tests;

import base.BaseTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.PlayListPage;

public class PlaylistTests extends BaseTests {

    @Test
    public void testPlaylistPageLoads() {
        driver.get("https://open.spotify.com/playlist/37i9dQZF1DXcBWIGoYBM5M");
        PlayListPage playlistPage = new PlayListPage(driver);

        Assert.assertTrue(driver.getCurrentUrl().contains("/playlist/"));
        Assert.assertTrue(playlistPage.getPlaylistTitle().length() > 0);
    }

    @Test
    public void testPlaylistTitleVisible() {
        driver.get("https://open.spotify.com/playlist/37i9dQZF1DXcBWIGoYBM5M");
        PlayListPage playlistPage = new PlayListPage(driver);

        Assert.assertFalse(playlistPage.getPlaylistTitle().isEmpty());
    }

    @Test
    public void testPlaylistHasTracks() {
        driver.get("https://open.spotify.com/playlist/37i9dQZF1DXcBWIGoYBM5M");
        PlayListPage playlistPage = new PlayListPage(driver);

        Assert.assertTrue(playlistPage.getTrackCount() > 0);
    }

    @Test

    public void testPlaylistPlayButtonVisible() {

        driver.get("https://open.spotify.com/playlist/37i9dQZF1DXcBWIGoYBM5M");

        PlayListPage playlistPage = new PlayListPage(driver);

        Assert.assertTrue(playlistPage.isPlayButtonVisible(), "Play button is not visible.");
    }

    @Test
    public void testPlaylistMoreOptionsVisible() {
        driver.get("https://open.spotify.com/playlist/37i9dQZF1DXcBWIGoYBM5M");
        PlayListPage playlistPage = new PlayListPage(driver);

        Assert.assertTrue(playlistPage.isMoreOptionsVisible());
    }
}
