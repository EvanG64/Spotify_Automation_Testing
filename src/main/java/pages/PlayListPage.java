package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PlayListPage extends BasePage {
    private By createPlaylistBtn = By.cssSelector("[data-testid='create-playlist-button']");
    private By playlistNameInput = By.cssSelector("[data-testid='playlist-edit-details-name-input']");
    private By saveBtn = By.cssSelector("[data-testid='playlist-edit-details-save-button']");
    private By playlistTitle = By.cssSelector("h1.Type__TypeElement-sc-goli3j-0");
    private By deleteOption = By.xpath("//span[text()='Delete']");

    public PlayListPage(WebDriver driver) {
        super(driver);
    }

    public void clickCreatePlaylist() {
        clickElement(driver.findElement(createPlaylistBtn));
    }

    public void editPlaylistName(String newName) {
        enterText(driver.findElement(playlistNameInput), newName);
        clickElement(driver.findElement(saveBtn));
    }

    public String getPlaylistTitle() {
        return driver.findElement(playlistTitle).getText();
    }

    public void deletePlaylist() {
        clickElement(driver.findElement(deleteOption)); // Usually requires right-click or context menu interaction first
    }
}