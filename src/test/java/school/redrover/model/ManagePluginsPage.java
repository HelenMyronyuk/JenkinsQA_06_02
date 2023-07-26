package school.redrover.model;

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

    public List<String> checkFourTasksOnTheLeftSidePanel() {
        List<WebElement> listOfTasks = getWait5().until(ExpectedConditions.visibilityOfAllElements(fourTasksOnTheLeftSidePanel));

        return TestUtils.getTexts(listOfTasks);
    }

    public ManagePluginsAdvancedPage clickAdvancedSettings(){
        advancedSettings.click();
        return new ManagePluginsAdvancedPage(getDriver());
    }
}
