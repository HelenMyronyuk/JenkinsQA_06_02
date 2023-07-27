package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.*;
import school.redrover.model.jobs.FolderPage;

public abstract class BaseOtherFoldersPage<Self extends BaseJobPage<?>> extends BaseJobPage<Self> {

    @FindBy(partialLinkText = "Delete ")
    private WebElement deleteButton;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement disableEnableButton;

    @FindBy(xpath = "//form[@method='post']")
    private WebElement disableMessage;

    @FindBy(xpath = "//div[@id='view-message']")
    private WebElement descriptionMessage;

    @FindBy(xpath = "//h1/*[@title='Folder']")
    private WebElement defaultIcon;

    @FindBy(xpath = "//h1/img")
    private WebElement metadataFolderIcon;

    @FindBy(xpath = "//a[contains(@href,'/console')]")
    private WebElement scanLog;

    @FindBy(xpath = "//a[contains(@href,'/events')]")
    private WebElement eventsLink;

    @FindBy(xpath = "//a[contains(@href, 'pipeline-syntax')]")
    private WebElement pipelineSyntax;

    @FindBy(xpath = "//a[contains(@href,'/asynchPeople/')]")
    private WebElement peopleButton;

    @FindBy(xpath = "//a[contains(@href,'/welcome/builds')]")
    private WebElement buildHistoryButton;

    public BaseOtherFoldersPage(WebDriver driver) {
        super(driver);
    }

    public DeletePage<MainPage> clickDeleteJobLocatedOnMainPage() {
        deleteButton.click();
        return new DeletePage<>(new MainPage(getDriver()));
    }

    public DeletePage<FolderPage> clickDeleteJobLocatedOnFolderPage() {
        deleteButton.click();
        return new DeletePage<>(new FolderPage(getDriver()));
    }

    public Self clickDisableEnableButton() {
        disableEnableButton.click();
        return (Self) this;
    }

    public String getTextFromDisableMessage() {
        return disableMessage.getText();
    }

    public String getAddedDescriptionFromConfig() {
        return descriptionMessage.getText();
    }

    public boolean isDefaultIconDisplayed() {
        return getWait5().until(ExpectedConditions.visibilityOf(defaultIcon)).isDisplayed();
    }

    public boolean isMetadataFolderIconDisplayed() {
        return getWait5().until(ExpectedConditions.visibilityOf(metadataFolderIcon)).isDisplayed();
    }

    public String getDisableButtonText() {
        return disableEnableButton.getText();
    }

    public ScanOtherFoldersLogPage clickScanLog() {
        getWait5().until(ExpectedConditions.elementToBeClickable(scanLog)).click();

        return new ScanOtherFoldersLogPage(getDriver());
    }

    public OtherFoldersEventsPage clickEventsLink() {
        getWait5().until(ExpectedConditions.elementToBeClickable(eventsLink)).click();

        return new OtherFoldersEventsPage(getDriver());
    }

    public PipelineSyntaxPage clickPipelineSyntax() {
        pipelineSyntax.click();

        return new PipelineSyntaxPage(getDriver());
    }

    public PeoplePage clickPeopleFromSideMenu(){
        getWait5().until(ExpectedConditions.elementToBeClickable(peopleButton)).click();
        return new PeoplePage(getDriver());
    }

    public WelcomeBuildHistoryPage clickBuildHistoryWelcomeFromSideMenu(){
        getWait5().until(ExpectedConditions.elementToBeClickable(buildHistoryButton)).click();
        return new WelcomeBuildHistoryPage(getDriver());
    }
}
