package school.redrover.model.base;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.*;
import school.redrover.model.base.baseConfig.BaseConfigPage;
import school.redrover.model.builds.WelcomeBuildHistoryPage;
import school.redrover.model.externalPages.DocBookPipelineMultibranchPage;
import school.redrover.model.externalPages.DocBookPipelinePage;
import school.redrover.model.jobs.FolderPage;
import school.redrover.model.jobsSidemenu.CredentialsPage;
import school.redrover.model.jobsSidemenu.OtherFoldersEventsPage;
import school.redrover.model.jobsSidemenu.PipelineSyntaxPage;

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

    @FindBy(xpath = "//a[contains(@href,'/credentials')]")
    private WebElement credentialsButton;

    @FindBy(xpath = "//a[@href='./configure']")
    private WebElement configureProject;

    @FindBy(xpath = "//*[@href='https://www.jenkins.io/doc/book/pipeline/']")
    private WebElement linkBookCreatingJenkinsPipeline;

    @FindBy(xpath = "//a[@href='https://www.jenkins.io/doc/book/pipeline/multibranch/']")
    private WebElement multibranchProject;

    public BaseOtherFoldersPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click on the 'Delete' on the side menu")
    public DeletePage<MainPage> clickDeleteJobLocatedOnMainPage() {
        deleteButton.click();

        return new DeletePage<>(new MainPage(getDriver()));
    }

    @Step("Click on the 'Delete' on the side menu")
    public DeletePage<FolderPage> clickDeleteJobLocatedOnFolderPage() {
        deleteButton.click();

        return new DeletePage<>(new FolderPage(getDriver()));
    }

    @Step("Click on the 'Disable/Enable' button")
    public Self clickDisableEnableButton() {
        disableEnableButton.click();

        return (Self) this;
    }

    @Step("Get text from Disable massage")
    public String getTextFromDisableMessage() {
        return disableMessage.getText();
    }

    @Step("Get text from field 'Description'")
    public String getAddedDescriptionFromConfig() {
        return descriptionMessage.getText();
    }

    @Step("Get boolean parameter from 'Default icon' that icon is Displayed")
    public boolean isDefaultIconDisplayed() {
        return getWait5().until(ExpectedConditions.visibilityOf(defaultIcon)).isDisplayed();
    }

    @Step("Folder icon is Displayed")
    public boolean isMetadataFolderIconDisplayed() {
        return getWait5().until(ExpectedConditions.visibilityOf(metadataFolderIcon)).isDisplayed();
    }

    @Step("Get text from the 'Disable/Enable' button")
    public String getDisableButtonText() {
        return disableEnableButton.getText();
    }

    @Step("Click on the 'Scan Log' button on the side menu")
    public ScanOtherFoldersLogPage clickScanLog() {
        getWait5().until(ExpectedConditions.elementToBeClickable(scanLog)).click();

        return new ScanOtherFoldersLogPage(getDriver());
    }

    @Step("Click on the 'Events' button on the side menu")
    public OtherFoldersEventsPage clickEventsLink() {
        getWait5().until(ExpectedConditions.elementToBeClickable(eventsLink)).click();

        return new OtherFoldersEventsPage(getDriver());
    }

    @Step("Click on the 'Pipeline Syntax' button on the side menu")
    public PipelineSyntaxPage clickPipelineSyntax() {
        pipelineSyntax.click();

        return new PipelineSyntaxPage(getDriver());
    }

    @Step("Click on the 'People' button on the side menu")
    public PeoplePage clickPeopleFromSideMenu() {
        getWait5().until(ExpectedConditions.elementToBeClickable(peopleButton)).click();

        return new PeoplePage(getDriver());
    }

    @Step("Click on the 'Build History' button on the side menu")
    public WelcomeBuildHistoryPage clickBuildHistoryWelcomeFromSideMenu() {
        getWait5().until(ExpectedConditions.elementToBeClickable(buildHistoryButton)).click();

        return new WelcomeBuildHistoryPage(getDriver());
    }

     @Step("Click on the 'Credentials' button on the side menu")
     public CredentialsPage clickCredentials() {
        getWait5().until(ExpectedConditions.elementToBeClickable(credentialsButton)).click();

        return new CredentialsPage(getDriver());
    }

    @Step("Set up and click on the 'Configure' button")
    protected void setupClickConfigureProject() {
        getWait10().until(ExpectedConditions.elementToBeClickable(configureProject)).click();
    }

    @Step("Click on the 'Configure' button")
    public abstract BaseConfigPage<?, ?> clickConfigureProject();

    @Step("Click on the 'Creating a Jenkins Pipeline' link on the Project page")
    public DocBookPipelinePage clickCreatingAJenkinsPipelineLinkOnProjectPage() {
        getWait5().until(ExpectedConditions.elementToBeClickable(linkBookCreatingJenkinsPipeline)).click();

        return new DocBookPipelinePage(getDriver());
    }

    @Step("Get text from the 'Creating a Jenkins Pipeline' link")
    public String getTextCreatingJenkinsPipeline() {
        return getWait5().until(ExpectedConditions.elementToBeClickable(linkBookCreatingJenkinsPipeline)).getText();
    }

    @Step("Click on the 'Creating Multibranch Projects' link on the Multibranch Project page")
    public DocBookPipelineMultibranchPage clickMultibranchProject() {
        getWait2().until(ExpectedConditions.elementToBeClickable(multibranchProject)).click();

        return new DocBookPipelineMultibranchPage(getDriver());
    }
}
