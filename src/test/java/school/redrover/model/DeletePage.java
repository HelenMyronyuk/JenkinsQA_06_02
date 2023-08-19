package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseJobPage;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.BasePage;

public class DeletePage<ParentPage extends BasePage<?,?>> extends BaseMainHeaderPage<DeletePage<ParentPage>> {

    @FindBy(xpath = "//form[@action='doDelete']")
    private WebElement confirmDeletionForm;

    @FindBy(name = "Submit")
    private WebElement deleteYesButton;

    private ParentPage parentPage;

    public DeletePage(ParentPage parentPage) {
        super(parentPage.getDriver());
        this.parentPage = parentPage;
    }

    public DeletePage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Click on the 'Yes' button on the DeletePage")
    public ParentPage clickYesButton() {
        deleteYesButton.click();

        return parentPage;
    }

    @Step("Click on the 'Delete' button on the DeletePage")
    public <JobPage extends BaseJobPage<?>> JobPage clickDelete(JobPage jobPage) {
        deleteYesButton.click();

        return jobPage;
    }

    @Step("Get text from Confirm Deletion Form")
    public String getTextFromConfirmDeletionForm() {
        return getWait5().until(ExpectedConditions.visibilityOf(confirmDeletionForm)).getText();
    }

    @Step("Check if delete button is displayed")
    public boolean isDeleteButtonDisplayed() {
        return getWait5().until(ExpectedConditions.visibilityOf(deleteYesButton)).isDisplayed();
    }
}
