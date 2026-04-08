package tests;

import base.BaseTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProfilePage;

public class ProfileSettingsTests extends BaseTests {

    @Test
    public void testVerifyProfileName() {
        ProfilePage profilePage = homePage.openProfileMenu();
        Assert.assertNotNull(profilePage.getProfileName());
    }

    @Test
    public void testChangeDisplayName() {
        ProfilePage profilePage = homePage.openProfileMenu();
        profilePage.clickEditProfile();
        profilePage.changeDisplayName("Automation User");
        Assert.assertEquals(profilePage.getProfileName(), "Automation User");
    }

    @Test
    public void testFollowersVisibility() {
        ProfilePage profilePage = homePage.openProfileMenu();
        // Assert follower count element is displayed
    }

    @Test
    public void testFollowingVisibility() {
        ProfilePage profilePage = homePage.openProfileMenu();
        // Assert following count element is displayed
    }

    @Test
    public void testSuccessfulLogout() {
        ProfilePage profilePage = homePage.openProfileMenu();
        profilePage.logout();
        // Assert URL redirects to login or homepage without auth
    }
}