package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.util.List;

public class BaseTests {

    protected WebDriver driver;

    protected static final String TEST_EMAIL    = "icecream20262023@gmail.com";
    protected static final String TEST_PASSWORD = "chocolate2026";

    @BeforeMethod
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        // Anti-detection: disable automation flag in Firefox
        options.addPreference("dom.webdriver.enabled", false);
        options.addPreference("useAutomationExtension", false);
        options.addPreference("general.useragent.override",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:124.0) Gecko/20100101 Firefox/124.0");

        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void skipIfCaptcha() {
        String url = driver.getCurrentUrl().toLowerCase();
        String src = driver.getPageSource().toLowerCase();
        if (url.contains("recaptcha") ||
                url.contains("challenge") ||
                src.contains("i'm not a robot") ||
                src.contains("im not a robot") ||
                src.contains("make sure that you're a human") ||
                src.contains("recaptcha")) {
            throw new SkipException(
                    "Skipped: Spotify CAPTCHA appeared — known limitation of automated testing."
            );
        }
    }

    protected void loginAsTestUser() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        pages.LoginPage loginPage = new pages.LoginPage(driver);

        driver.get("https://accounts.spotify.com/en/login");
        loginPage.waitForPage();
        loginPage.setEmail(TEST_EMAIL);
        loginPage.clickContinue();

        Thread.sleep(1000);
        clickLogInWithPasswordAnywhere();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[type='password'], input[id='login-password'], input[name='password']")
        ));
        loginPage.setPassword(TEST_PASSWORD);
        loginPage.clickLogIn();

        wait.until(d ->
                !d.getCurrentUrl().contains("accounts.spotify.com/en/login") &&
                        !d.getCurrentUrl().contains("challenge.spotify.com")
        );
    }

    private void clickLogInWithPasswordAnywhere() throws InterruptedException {
        String[] xpaths = {
                "//a[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'log in with a password')]",
                "//button[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'log in with a password')]",
                "//*[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'log in with a password')]",
                "//*[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'log in with password')]",
        };

        for (int attempt = 0; attempt < 6; attempt++) {
            for (String xpath : xpaths) {
                try {
                    List<WebElement> elements = driver.findElements(By.xpath(xpath));
                    for (WebElement el : elements) {
                        if (el.isDisplayed()) {
                            ((JavascriptExecutor) driver).executeScript(
                                    "arguments[0].scrollIntoView(true); arguments[0].click();", el
                            );
                            return;
                        }
                    }
                } catch (Exception ignored) {}
            }
            Thread.sleep(500);
        }
    }
}