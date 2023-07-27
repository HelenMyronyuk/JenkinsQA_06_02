package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.MainPage;
import school.redrover.model.MovePage;
import school.redrover.model.RenamePage;
import school.redrover.model.base.baseConfig.BaseConfigPage;

import java.time.Duration;

public abstract class BaseJobPage<Self extends BaseJobPage<?>> extends BaseMainHeaderPage<Self> {

    @FindBy(linkText = "Configure")
    private WebElement configureButton;

    @FindBy(xpath = "//h1")
    private WebElement jobName;

    @FindBy(linkText = "Rename")
    private WebElement renameButton;

    @FindBy(partialLinkText = "Delete ")
    private WebElement deleteButton;

    @FindBy(xpath = "//a[@id='description-link']")
    private WebElement addEditDescriptionButton;

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement descriptionTextarea;

    @FindBy(xpath = "//span[text()='Move']/..")
    private WebElement moveButton;

    @FindBy(xpath = "//button[text()='Save']")
    private WebElement saveButton;

    @FindBy(xpath = "//div[@id='main-panel']")
    private WebElement mainPanel;

    @FindBy(xpath = "//a[@class='textarea-show-preview']")
    private WebElement preview;

    @FindBy(xpath = "//div[@class='textarea-preview']")
    private WebElement previewTextarea;

    @FindBy(xpath = "//div[@id=\"description\"]/div")
    private WebElement textDescription;

    public BaseJobPage(WebDriver driver) {
        super(driver);
    }

    protected void setupClickConfigure() {
        getWait10().until(ExpectedConditions.elementToBeClickable(configureButton)).click();
    }

    public abstract BaseConfigPage<?,?> clickConfigure();

    public String getJobName() {
        return getWait2().until(ExpectedConditions.visibilityOf(jobName)).getText();
    }

    public RenamePage<Self> clickRename() {
        renameButton.click();
        return new RenamePage<>((Self) this);
    }

    public MainPage clickDeleteAndAlert() {
        deleteButton.click();
        getDriver().switchTo().alert().accept();
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(2));
        return new MainPage(getDriver());
    }

    public Self clickEditDescription() {
        getWait5().until(ExpectedConditions.visibilityOf(addEditDescriptionButton)).click();
        return (Self) this;
    }

    public Self enterDescription(String description) {
        descriptionTextarea.sendKeys(description);
        return (Self) this;
    }

    public Self clearDescriptionField() {
        getWait5().until(ExpectedConditions.visibilityOf(descriptionTextarea)).clear();
        return (Self) this;
    }

    public String getTextDescription() {
        return getWait5().until(ExpectedConditions.visibilityOf(textDescription)).getText();
    }

    public boolean isDescriptionEmpty() {
        return textDescription.getText().isEmpty();
    }

    public MovePage<Self> clickMoveOnSideMenu() {
        getWait5().until(ExpectedConditions.elementToBeClickable(moveButton)).click();
        return new MovePage<>((Self) this);
    }

    public Self clickAddEditDescription(String newDescription) {
        addEditDescriptionButton.click();
        getWait2().until(ExpectedConditions.elementToBeClickable(descriptionTextarea));
        descriptionTextarea.clear();
        descriptionTextarea.sendKeys(newDescription);
        return (Self) this;
    }

    public Self clickSaveButton() {
        saveButton.click();
        return (Self) this;
    }

    public String getProjectNameSubtitleWithDisplayName() {
        String projectName = mainPanel.getText();
        String subStr = projectName.substring(projectName.indexOf(':') + 2);
        return subStr.substring(0, subStr.indexOf("\n")).trim();
    }

    public Self clickAddDescription() {
        getWait5().until(ExpectedConditions.visibilityOf(addEditDescriptionButton)).click();
        return (Self) this;
    }

    public Self clickPreview() {
        preview.click();
        return (Self) this;
    }

    public String getPreviewText() {
        return getWait5().until(ExpectedConditions.visibilityOf(previewTextarea)).getText();
    }

    public String getDescriptionButton() {
        return addEditDescriptionButton.getText();
    }

    public String getProjectName() {
        String projectName = jobName.getText();
        if (projectName.contains("Project") || projectName.contains("Pipeline")) {
            return projectName.substring(projectName.indexOf(" ") + 1);
        } else {
            return projectName;
        }
    }
}
