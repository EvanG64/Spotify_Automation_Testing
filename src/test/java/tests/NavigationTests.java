package tests;

import base.BaseTests;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class NavigationTests extends BaseTests {

    private WebDriverWait wait;

    public void setupWait() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    public void testNavigateToHome() {
        setupWait();

        driver.get("https://open.spotify.com/");
        wait.until(ExpectedConditions.urlContains("spotify"));

        Assert.assertTrue(driver.getCurrentUrl().contains("spotify"));
    }

    @Test
    public void testNavigateToSearch() {
        setupWait();

        driver.get("https://open.spotify.com/search");
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("search"),
                ExpectedConditions.titleContains("Spotify")
        ));

        String currentUrl = driver.getCurrentUrl().toLowerCase();
        String pageSource = driver.getPageSource().toLowerCase();

        Assert.assertTrue(
                currentUrl.contains("search")
                        || pageSource.contains("what do you want to play")
                        || pageSource.contains("search"),
                "Search page did not load correctly."
        );
    }

    @Test
    public void testNavigateToLibrary() {
        setupWait();

        driver.get("https://open.spotify.com/collection");
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("collection"),
                ExpectedConditions.urlContains("login"),
                ExpectedConditions.titleContains("Spotify")
        ));

        Assert.assertTrue(
                driver.getCurrentUrl().contains("collection")
                        || driver.getCurrentUrl().contains("login")
                        || driver.getTitle().contains("Spotify")
        );
    }

    @Test
    public void testNavigateToPremium() {
        setupWait();

        driver.get("https://www.spotify.com/premium/");
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("premium"),
                ExpectedConditions.titleContains("Premium"),
                ExpectedConditions.titleContains("Spotify")
        ));

        Assert.assertTrue(
                driver.getCurrentUrl().toLowerCase().contains("premium")
                        || driver.getTitle().toLowerCase().contains("premium")
                        || driver.getTitle().toLowerCase().contains("spotify")
        );
    }

    @Test
    public void testBrowserBackNavigation() {
        setupWait();

        driver.get("https://open.spotify.com/");
        wait.until(ExpectedConditions.titleContains("Spotify"));

        driver.get("https://open.spotify.com/search");
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("search"),
                ExpectedConditions.titleContains("Spotify")
        ));

        driver.navigate().back();

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlToBe("https://open.spotify.com/"),
                ExpectedConditions.urlContains("open.spotify.com"),
                ExpectedConditions.titleContains("Spotify")
        ));

        String currentUrl = driver.getCurrentUrl().toLowerCase();
        String pageSource = driver.getPageSource().toLowerCase();

        Assert.assertTrue(
                currentUrl.contains("open.spotify.com")
                        && !currentUrl.contains("search")
                        || pageSource.contains("trending songs")
                        || pageSource.contains("popular artists")
                        || pageSource.contains("preview of spotify"),
                "Back navigation did not return to a valid Spotify home/public page."
        );
    }
}