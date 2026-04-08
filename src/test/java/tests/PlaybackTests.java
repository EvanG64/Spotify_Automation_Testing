package tests;

import base.BaseTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.PlaybackPage;
import pages.SearchPage;

public class PlaybackTests extends BaseTests {

    @Test
    public void testPlayPauseToggle() {
        // Assume user navigates to a song first
        PlaybackPage playbackPage = new PlaybackPage(driver);
        playbackPage.togglePlayPause();
        // Assert state changed to Playing
    }

    @Test
    public void testSkipForward() {
        PlaybackPage playbackPage = new PlaybackPage(driver);
        String currentSong = playbackPage.getNowPlayingTitle();
        playbackPage.skipForward();
        Assert.assertNotEquals(playbackPage.getNowPlayingTitle(), currentSong);
    }

    @Test
    public void testSkipBackward() {
        PlaybackPage playbackPage = new PlaybackPage(driver);
        playbackPage.skipBackward();
        // Assert song restarts or goes to previous
    }

    @Test
    public void testShuffleToggle() {
        PlaybackPage playbackPage = new PlaybackPage(driver);
        playbackPage.toggleShuffle();
        // Assert shuffle icon changes to active state
    }

    @Test
    public void testVolumeControlExists() {
        // Example of checking UI state without playback
        PlaybackPage playbackPage = new PlaybackPage(driver);
        // Assert volume slider is visible and interactable
    }
}