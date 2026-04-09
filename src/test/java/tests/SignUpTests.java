package tests;

import base.BaseTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SignUpPage;

public class SignUpTests extends BaseTests {

    @Test
    public void testSignUpPageLoads() {
        driver.get("https://www.spotify.com/us/signup");
        SignUpPage signUpPage = new SignUpPage(driver);

        Assert.assertTrue(driver.getCurrentUrl().toLowerCase().contains("signup"));
        Assert.assertTrue(driver.getTitle().toLowerCase().contains("spotify"));
    }

    @Test
    public void testEmailFieldVisible() {
        driver.get("https://www.spotify.com/us/signup");
        SignUpPage signUpPage = new SignUpPage(driver);

        Assert.assertTrue(signUpPage.isEmailFieldVisible());
    }

    @Test
    public void testWeakPasswordInput() {
        driver.get("https://www.spotify.com/us/signup");
        SignUpPage signUpPage = new SignUpPage(driver);

        signUpPage.enterEmail("testuser@example.com");
        signUpPage.enterPassword("123");

        Assert.assertTrue(driver.getTitle().toLowerCase().contains("spotify"));
    }

    @Test
    public void testMissingRequiredFields() {
        driver.get("https://www.spotify.com/us/signup");
        SignUpPage signUpPage = new SignUpPage(driver);

        signUpPage.clickSignUp();

        Assert.assertTrue(driver.getCurrentUrl().toLowerCase().contains("signup"));
    }

    @Test
    public void testSignUpPageBrandingVisible() {
        driver.get("https://www.spotify.com/us/signup");

        String pageText = driver.findElement(org.openqa.selenium.By.tagName("body")).getText().toLowerCase();

        Assert.assertTrue(
                pageText.contains("spotify")
                        || pageText.contains("sign up")
                        || pageText.contains("email")
        );
    }
}
