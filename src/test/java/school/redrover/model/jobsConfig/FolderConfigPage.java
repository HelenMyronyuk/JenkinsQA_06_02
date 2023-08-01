package school.redrover.model.jobsConfig;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import school.redrover.model.jobs.FolderPage;
import school.redrover.model.base.baseConfig.BaseConfigFoldersPage;
import school.redrover.runner.TestUtils;

public class FolderConfigPage extends BaseConfigFoldersPage<FolderConfigPage, FolderPage> {

    @FindBy(xpath = "//div[@class='repeated-container with-drag-drop']/span")
    private WebElement addButton;

    @FindBy(xpath = "//input[@checkdependson='name']")
    private WebElement nameField;

    @FindBy(xpath = "//input[@name='_.defaultVersion']")
    private WebElement defaultVersionField;

    @FindBy(xpath = "//div[contains(text(), 'Source Code')]/../div/select")
    private WebElement sourceCodeManagementOptions;

    @FindBy(xpath = "//input[@name='_.repositoryUrl']")
    private WebElement repositoryField;

    @FindBy(xpath = "//div[@class='ok']")
    private WebElement currentDefaultVersion;

    @FindBy(xpath = "//button[@data-section-id='properties']")
    private WebElement propertiesButton;

    @FindBy(tagName = "footer")
    private WebElement footer;

    @FindBy(xpath = "//label[contains(text(), 'Repository Scan')]")
    private WebElement repositoryScanRadio;

    @FindBy(xpath = "//label[@class='attach-previous ']")
    private WebElement cacheFetchedLabel;

    public FolderConfigPage(FolderPage folderPage) {
        super(folderPage);
    }

    public FolderConfigPage inputNameLibrary() {
        TestUtils.scrollWithPauseByActions(this, footer, 500);
        getWait2().until(ExpectedConditions.elementToBeClickable(addButton)).click();
        nameField.sendKeys("shared-library");

        return this;
    }

    public FolderConfigPage inputDefaultVersion(String defaultVersion) {
       TestUtils.scrollWithPauseByActions(this, cacheFetchedLabel, 100);
       getWait2().until(ExpectedConditions.elementToBeClickable(defaultVersionField)).sendKeys(defaultVersion);

        return this;
    }

    public FolderConfigPage pushSourceCodeManagementButton() {
        TestUtils.scrollWithPauseByActions(this, sourceCodeManagementOptions, 300);
        getWait2().until(ExpectedConditions.elementToBeClickable(sourceCodeManagementOptions)).click();

        return this;
    }

    public FolderConfigPage chooseOptionGitHub() {
        new Select(sourceCodeManagementOptions).selectByVisibleText("GitHub");

        return this;
    }

    public FolderConfigPage inputLibraryRepoUrl(String repoUrl) {
        TestUtils.scrollWithPauseByActions(this, repositoryScanRadio, 100);
        getWait2().until(ExpectedConditions.elementToBeClickable(repositoryField)).sendKeys(repoUrl);

        return this;
    }

    public FolderConfigPage refreshPage() {
        getDriver().navigate().refresh();

        return this;
    }

    public Boolean libraryDefaultVersionValidated() {
        TestUtils.scrollWithPauseByActions(this, cacheFetchedLabel, 100);

        return getWait5().until(ExpectedConditions.visibilityOf(currentDefaultVersion)).getText().contains("Currently maps to revision");
    }
}
