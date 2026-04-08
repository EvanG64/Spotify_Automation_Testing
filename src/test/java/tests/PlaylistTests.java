package tests;

import base.BaseTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.PlaylistPage;

public class PlaylistTests extends BaseTests {

    @Test
    public void testCreateNewPlaylist() {
        PlaylistPage playlistPage = new PlaylistPage(driver);
        playlistPage.clickCreatePlaylist();
        Assert.assertTrue(playlistPage.getPlaylistTitle().contains("My Playlist"));
    }

    @Test
    public void testRenamePlaylist() {
        PlaylistPage playlistPage = new PlaylistPage(driver);
        String newName = "Study Focus";
        playlistPage.editPlaylistName(newName);
        Assert.assertEquals(playlistPage.getPlaylistTitle(), newName);
    }

    @Test
    public void testAddSongToPlaylist() {
        // Navigate, search song, click add to playlist context menu
        // Assert song appears in playlist view
    }

    @Test
    public void testRemoveSongFromPlaylist() {
        // Open playlist, click remove on a song
        // Assert song count decreases
    }

    @Test
    public void testDeletePlaylist() {
        PlaylistPage playlistPage = new PlaylistPage(driver);
        playlistPage.deletePlaylist();
        // Assert playlist no longer appears in sidebar navigation
    }
}