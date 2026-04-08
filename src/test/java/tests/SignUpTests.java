package tests;

import base.BaseTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SignUpPage;

public class SignUpTests extends BaseTests {

    @Test
    public void testSuccessfulSignUp() {
        // Assume homePage has a clickSignUpNav() method
        SignUpPage signUpPage = homePage.clickSignUpNav();

        // Use a dynamic email to avoid "email already taken" errors on subsequent runs
        String dynamicEmail = "testuser_" + System.currentTimeMillis() + "@testdomain.com";

        signUpPage.enterEmail(dynamicEmail);
        signUpPage.enterPassword("SecurePass123!@#");
        signUpPage.enterDisplayName("Automation Tester");
        signUpPage.setDateOfBirth("15", "March", "1995");
        signUpPage.selectGender();
        signUpPage.acceptTerms();
        signUpPage.clickSignUp();

        // Assert that the user is redirected to the logged-in homepage or profile setup
        Assert.assertFalse(driver.getCurrentUrl().contains("signup"));
    }

    @Test
    public void testSignUpWithExistingEmail() {
        SignUpPage signUpPage = homePage.clickSignUpNav();

        // Use an email you know is already registered
        signUpPage.enterEmail("alreadyregistered@test.com");
        signUpPage.enterPassword("SomePassword123!");
        signUpPage.enterDisplayName("Duplicate User");
        signUpPage.setDateOfBirth("01", "January", "1990");
        signUpPage.selectGender();
        signUpPage.acceptTerms();
        signUpPage.clickSignUp();

        Assert.assertTrue(signUpPage.getEmailErrorMessage().contains("already associated with an account"));
    }

    @Test
    public void testSignUpWithWeakPassword() {
        SignUpPage signUpPage = homePage.clickSignUpNav();

        signUpPage.enterEmail("newuser_weakpass@test.com");
        signUpPage.enterPassword("123"); // Password too short
        signUpPage.enterDisplayName("Weak Pass User");
        signUpPage.clickSignUp();

        // Assert that an error banner or specific password error is shown
        Assert.assertTrue(signUpPage.isErrorBannerDisplayed());
    }

    @Test
    public void testMissingRequiredFields() {
        SignUpPage signUpPage = homePage.clickSignUpNav();

        // Enter only the email, leaving Password, Name, and DOB blank
        signUpPage.enterEmail("missingfields@test.com");
        signUpPage.clickSignUp();

        // Assert that validation prevents submission
        Assert.assertTrue(driver.getCurrentUrl().contains("signup"));
    }

    @Test
    public void testUnderageSignUpAttempt() {
        SignUpPage signUpPage = homePage.clickSignUpNav();

        signUpPage.enterEmail("underage_user@test.com");
        signUpPage.enterPassword("SecurePass123!");
        signUpPage.enterDisplayName("Young User");

        // Calculate a year that makes the user too young (e.g., born this year)
        signUpPage.setDateOfBirth("01", "January", "2024");
        signUpPage.selectGender();
        signUpPage.acceptTerms();
        signUpPage.clickSignUp();

        // Assert that the system catches the age restriction
        Assert.assertTrue(signUpPage.isErrorBannerDisplayed());
    }
}