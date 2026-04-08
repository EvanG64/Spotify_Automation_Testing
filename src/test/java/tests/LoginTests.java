package tests;

import base.BaseTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTests extends BaseTests {

    @Test
    public void testSuccessfulLogin() {
        // Assume homePage has a clickLoginNav() method that returns a LoginPage
        LoginPage loginPage = homePage.clickLoginNav();
        loginPage.setUsername("your_valid_email@test.com");
        loginPage.setPassword("ValidPassword123!");
        loginPage.clickLogin();
        // Assert that the URL changed or a profile icon is visible
    }

    @Test
    public void testInvalidPassword() {
        LoginPage loginPage = homePage.clickLoginNav();
        loginPage.setUsername("your_valid_email@test.com");
        loginPage.setPassword("WrongPassword");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains("Incorrect username or password"));
    }

    @Test
    public void testInvalidEmailFormat() {
        LoginPage loginPage = homePage.clickLoginNav();
        loginPage.setUsername("invalidemail.com");
        loginPage.setPassword("SomePassword");
        loginPage.clickLogin();
        // Assert specific validation message
    }

    @Test
    public void testEmptyFields() {
        LoginPage loginPage = homePage.clickLoginNav();
        loginPage.clickLogin();
        // Assert validation messages for empty fields
    }

    @Test
    public void testSQLInjectionAttempt() {
        LoginPage loginPage = homePage.clickLoginNav();
        loginPage.setUsername("' OR 1=1 --");
        loginPage.setPassword("password");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains("Incorrect username or password"));
    }
}