package school.redrover.model.builds;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;

public class WorkspacesBuildPage extends BaseMainHeaderPage<WorkspacesBuildPage> {

    @FindBy(xpath = "//h1")
    private WebElement headerText;

    public WorkspacesBuildPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get Heading text from Workspaces Page ")
    public String getHeaderTextFromWorkspacesBuildPage() {
        return getWait5().until(ExpectedConditions.visibilityOf(headerText)).getText();
    }
}
