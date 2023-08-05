package school.redrover.model.manageJenkins;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.runner.TestUtils;

import java.util.List;

public class ManagePluginsPage extends BaseMainHeaderPage<ManagePluginsPage> {

    @FindBy(xpath = "//div[@id='tasks']//a")
    private List<WebElement> fourTasksOnTheLeftSidePanel;

    @FindBy(xpath = "//a[contains(@href,'/advanced')]")
    private WebElement advancedSettings;

    public ManagePluginsPage(WebDriver driver) {
        super(driver);
    }

    @Step("check tasks on the Left side panel from Manage Plugins Page")
    public List<String> checkTasksOnTheLeftSidePanel() {
        List<WebElement> listOfTasks = getWait5().until(ExpectedConditions.visibilityOfAllElements(fourTasksOnTheLeftSidePanel));

        return TestUtils.getTexts(listOfTasks);
    }

    @Step("Click on the 'Advanced Settings' link on the Manage Plugins Page")
    public ManagePluginsAdvancedPage clickAdvancedSettings(){
        advancedSettings.click();

        return new ManagePluginsAdvancedPage(getDriver());
    }
}
