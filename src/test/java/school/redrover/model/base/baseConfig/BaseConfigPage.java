package school.redrover.model.base.baseConfig;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;

public abstract class BaseConfigPage<Self extends BaseConfigPage<?, ?>, JobPage extends BaseMainHeaderPage<?>> extends BaseMainHeaderPage<Self> {

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//textarea[contains(@name, 'description')]")
    private WebElement descriptionTextBox;

    @FindBy(xpath = "//a[contains(@previewendpoint, 'previewDescription')]")
    private WebElement preview;

    @FindBy(xpath = "//div[@class='textarea-preview']")
    private WebElement previewTextarea;

    @FindBy(xpath = "//h1")
    private WebElement title;

    @FindBy(xpath = "//button[@name='Apply']")
    private WebElement applyButton;

    @FindBy(xpath = "//div[@id='notification-bar'][contains(@class, 'jenkins-notification--visible')]")
    private WebElement notificationSuccess;

    private final JobPage jobPage;

    public BaseConfigPage(JobPage jobPage) {
        super(jobPage.getDriver());
        this.jobPage = jobPage;
    }

    protected JobPage getJobPage() {
        return jobPage;
    }

    @Step("Click 'Save' button on the BaseConfigPage page")
    public JobPage clickSaveButton() {
        getWait10().until(ExpectedConditions.elementToBeClickable(saveButton)).click();

        return getJobPage();
    }

    @Step("Get description")
    public String getDescription() {
        return descriptionTextBox.getText();
    }

    @Step("Add description")
    public Self addDescription(String description) {
        getWait5().until(ExpectedConditions.visibilityOf(descriptionTextBox)).sendKeys(description);

        return (Self) this;
    }

    @Step("Click preview")
    public Self clickPreview() {
        preview.click();

        return (Self) this;
    }

    @Step("Get text from preview")
    public String getPreviewText() {
        return previewTextarea.getText();
    }

    @Step("Clear description`s field")
    public Self clearDescriptionArea() {
        descriptionTextBox.clear();

        return (Self) this;
    }

    @Step("Get header text")
    public String getHeaderText() {
        return title.getText();
    }

    @Step("Push the 'Apply' button")
    public Self pushApply() {
        applyButton.click();
        getWait2().until(ExpectedConditions.visibilityOf(notificationSuccess));

        return (Self) this;
    }
}
