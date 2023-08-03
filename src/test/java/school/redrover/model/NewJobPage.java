package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.baseConfig.BaseConfigPage;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.runner.TestUtils;


public class NewJobPage extends BaseMainHeaderPage<NewJobPage> {

    @FindBy(xpath = "//button[@id='ok-button']")
    private WebElement okButton;

    @FindBy(xpath = "//input[@id='name']")
    private WebElement itemName;

    @FindBy(id = "itemname-required")
    private WebElement itemNameRequiredMessage;

    @FindBy(id = "itemname-invalid")
    private WebElement itemInvalidNameMessage;

    @FindBy(xpath = "//div[@class='header']//label")
    private WebElement header;

    private TestUtils.JobType jobType;

    public NewJobPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get job type")
    public TestUtils.JobType getJobType() {
        return jobType;
    }

    @Step("The button is enabled")
    public boolean isOkButtonEnabled() {
        return getWait5().until(ExpectedConditions.visibilityOf(okButton)).isEnabled();
    }

    @Step("Input the New Item name")
    public NewJobPage enterItemName(String jobName) {
        getWait5().until(ExpectedConditions.visibilityOf(itemName)).sendKeys(jobName);

        return this;
    }

    @Step("Select Job Type on NewJobPage")
    public NewJobPage selectJobType(TestUtils.JobType jobType) {
        getDriver().findElement(jobType.getLocator()).click();
        this.jobType = jobType;

        return this;
    }

    @Step("Click 'OK' button")
    public <JobConfigPage extends BaseConfigPage<?, ?>> JobConfigPage clickOkButton(JobConfigPage jobConfigPage) {
        getWait5().until(ExpectedConditions.visibilityOf(okButton)).click();

        return jobConfigPage;
    }

    @Step("Create a project and get an error")
    public CreateItemErrorPage selectJobAndOkAndGoError(TestUtils.JobType jobType) {
        selectJobType(jobType);
        clickOkButton(null);

        return new CreateItemErrorPage(getDriver());
    }

    @Step("Get an error message that invalid data is being used")
    public String getItemInvalidMessage() {
        return getWait5().until(ExpectedConditions.visibilityOf(getItemInvalidNameMessage())).getText();
    }

    @Step("Get message with invalid data ")
    private WebElement getItemInvalidNameMessage() {
        return itemInvalidNameMessage;
    }

    @Step("Get an error text with invalid name")
    public String getItemNameRequiredErrorText() {
        return getWait2().until(ExpectedConditions.visibilityOf(itemNameRequiredMessage)).getText();
    }

    @Step("Select Job Type on NewJobPage, Click 'OK' button ang go To Bug Page")
    public CreateBugPage selectJobAndOkAndGoToBugPage(TestUtils.JobType jobType) {
        selectJobType(jobType);
        clickOkButton(null);

        return new CreateBugPage(getDriver());
    }

    @Step("Get header text")
    public String getHeaderText () {
        return header.getText();
    }
}
