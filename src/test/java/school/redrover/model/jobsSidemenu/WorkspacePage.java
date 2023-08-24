package school.redrover.model.jobsSidemenu;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.BaseSubmenuPage;


public class WorkspacePage extends BaseSubmenuPage<WorkspacePage> {

    @FindBy(xpath = "//h1")
    private WebElement headerText;

    public WorkspacePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "Workspace";
    }

    @Step("Get Heading text from Workspaces Page ")
    public String getTextFromWorkspacePage() {
        return getWait5().until(ExpectedConditions.visibilityOf(headerText)).getText();
    }
}
