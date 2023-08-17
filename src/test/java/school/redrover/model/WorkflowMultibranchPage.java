package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;

public class WorkflowMultibranchPage extends BaseMainHeaderPage<WorkflowMultibranchPage> {

    @FindBy(xpath = "//h1")
    private WebElement workflowMultibranchPageTitle;

    public WorkflowMultibranchPage(WebDriver driver) {
        super(driver);
    }

    @Step("Switch to the Workflow Multibranch page in the other window")
    public WorkflowMultibranchPage switchToWorkflowMultibranchPage() {
        String originalWindow = getDriver().getWindowHandle();

        getWait10().until(numberOfWindowsToBe(2));

        assert getDriver().getWindowHandles().size() == 2;

        for (String winHandle : getDriver().getWindowHandles()) {
            if (!originalWindow.contentEquals(winHandle)) {
                getDriver().switchTo().window(winHandle);
                break;
            }
        }

        return this;
    }

    @Step("Get title from the Workflow Multibranch page")
    public String getWorkflowMultibranchPageTitle() {
        return getWait10().until(ExpectedConditions.visibilityOf(workflowMultibranchPageTitle)).getText();
    }
}
