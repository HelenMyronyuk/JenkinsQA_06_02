package school.redrover.model.jobs;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.*;

import school.redrover.model.base.BaseOtherFoldersPage;
import school.redrover.model.jobsConfig.OrganizationFolderConfigPage;

public class OrganizationFolderPage extends BaseOtherFoldersPage<OrganizationFolderPage> {

    @FindBy(xpath = "//a[contains(@href,'/computation/console')]")
    private WebElement scanButton;

    @FindBy(xpath = "//a[contains(@href, '/events')]")
    private WebElement eventButton;

    @FindBy(xpath = "//a[contains(@href,'/credentials')]")
    private WebElement credentialsButton;

    @FindBy(xpath = "//*[@href='https://www.jenkins.io/doc/book/pipeline/']")
    private WebElement linkBookCreatingJenkinsPipeline;

    @FindBy(xpath = "//a[@href='./configure']")
    private WebElement configureProject;

    @FindBy(xpath = "//span[(text() = 'Re-run the Folder Computation')]")
    private WebElement rerunFolderComputationLink;

    public OrganizationFolderPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public OrganizationFolderConfigPage clickConfigure() {
        setupClickConfigure();

        return new OrganizationFolderConfigPage(this);
    }

    public OrganizationFolderConfigPage clickConfigureProject() {
        getWait2().until(ExpectedConditions.elementToBeClickable(configureProject)).click();

        return new OrganizationFolderConfigPage(this);
    }

    public ScanOtherFoldersLogPage clickScanOrgFolderLog() {
        getWait5().until(ExpectedConditions.elementToBeClickable(scanButton)).click();

        return new ScanOtherFoldersLogPage(getDriver());
    }

    public OtherFoldersEventsPage clickOrgFolderEvents() {
        getWait5().until(ExpectedConditions.elementToBeClickable(eventButton)).click();

        return new OtherFoldersEventsPage(getDriver());
    }

    public CredentialsPage clickCredentials() {
        getWait5().until(ExpectedConditions.elementToBeClickable(credentialsButton)).click();

        return new CredentialsPage(getDriver());
    }

    public String getTextCreatingJenkinsPipeline() {

        return getWait5().until(ExpectedConditions.elementToBeClickable(linkBookCreatingJenkinsPipeline)).getText();
    }

    public ScanOtherFoldersPage clickRerunTheFolderComputation() {
        getWait5().until(ExpectedConditions.elementToBeClickable(rerunFolderComputationLink)).click();

        return new ScanOtherFoldersPage(getDriver());
    }

    public DocBookPipelinePage clickPipelineOneTutorial() {
        getWait5().until(ExpectedConditions.elementToBeClickable(linkBookCreatingJenkinsPipeline)).click();

        return new DocBookPipelinePage(getDriver());
    }
}