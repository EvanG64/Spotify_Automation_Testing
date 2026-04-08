package tests;

import base.BaseTests;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NavigationTests extends BaseTests {

    @Test
    public void testNavigateToHome() {
        // Assert URL is base URL
    }

    @Test
    public void testNavigateToSearch() {
        homePage.navigateToSearch();
        Assert.assertTrue(driver.getCurrentUrl().contains("/search"));
    }

    @Test
    public void testNavigateToLibrary() {
        // Assert navigation to Library section
    }

    @Test
    public void testNavigateToPremium() {
        // Assert navigation to Premium upgrade page
    }

    @Test
    public void testBrowserBackNavigation() {
        homePage.navigateToSearch();
        driver.navigate().back();
        // Assert returned to home page
    }
}