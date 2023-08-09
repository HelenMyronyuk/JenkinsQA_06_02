package school.redrover.model.jobsConfig;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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

    @FindBy(xpath = "//span[text()='Fresh clone per build']")
    private WebElement freshClonePerBuildLabel;

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

    @Step("Enter a library name in 'Name' input field under 'Library'")
    public FolderConfigPage inputNameLibrary() {
        TestUtils.scrollWithPauseByActions(this, footer, 500);
        getWait2().until(ExpectedConditions.elementToBeClickable(addButton)).click();
        nameField.sendKeys("shared-library");

        return this;
    }

    @Step("Enter a '{defaultVersion}' value in the 'Default version' input field")
    public FolderConfigPage inputDefaultVersion(String defaultVersion) {
       TestUtils.scrollWithPauseByActions(this, cacheFetchedLabel, 300);
       getWait2().until(ExpectedConditions.elementToBeClickable(defaultVersionField)).sendKeys(defaultVersion);

        return this;
    }

    @Step("Open a 'Source Code Management' dropdown menu")
    public FolderConfigPage openSourceCodeManagementDropdown() {
        TestUtils.scrollWithPauseByActions(this, freshClonePerBuildLabel, 500);
        getWait2().until(ExpectedConditions.elementToBeClickable(sourceCodeManagementOptions)).click();

        return this;
    }

    @Step("Select 'GitHub' option")
    public FolderConfigPage selectOptionGitHub() {
        new Select(sourceCodeManagementOptions).selectByVisibleText("GitHub");

        return this;
    }

    @Step("Enter '{repoUrl}' value in 'Library Path' input field")
    public FolderConfigPage inputLibraryRepoUrl(String repoUrl) {
        TestUtils.scrollWithPauseByActions(this, repositoryScanRadio, 300);
        getWait2().until(ExpectedConditions.elementToBeClickable(repositoryField)).sendKeys(repoUrl);

        return this;
    }

    @Step("Refresh the page")
    public FolderConfigPage refreshPage() {
        getDriver().navigate().refresh();

        return this;
    }

    @Step("Validate 'currentDefaultVersion' label")
    public Boolean libraryDefaultVersionValidated() {
        TestUtils.scrollWithPauseByActions(this, cacheFetchedLabel, 300);

        return getWait5().until(ExpectedConditions.visibilityOf(currentDefaultVersion)).getText().contains("Currently maps to revision");
    }
}
