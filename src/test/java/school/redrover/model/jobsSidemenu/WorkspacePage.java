package school.redrover.model.jobsSidemenu;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;


public class WorkspacePage extends BaseMainHeaderPage<WorkspacePage> {

    @FindBy(xpath = "//h1")
    private WebElement headerText;

    public WorkspacePage(WebDriver driver) {
        super(driver);
    }

    @Step("Get Heading text from Workspaces Page ")
    public String getTextFromWorkspacePage() {
        return getWait5().until(ExpectedConditions.visibilityOf(headerText)).getText();
    }
}
