package school.redrover.model.externalPages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseModel;

public class DocBookPipelineMultibranchPage extends BaseModel {

    @FindBy(xpath = "//h1[@id='branches-and-pull-requests']")
    private WebElement branchesAndPullRequestsTutorial;

    @FindBy(xpath = "//h1")
    private WebElement header;

    public DocBookPipelineMultibranchPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get text 'Branches and Pull Requests' ")
    public String getBranchesAndPullRequestsTutorial() {
        return branchesAndPullRequestsTutorial.getText();
    }

    @Step("Get text from Heading page")
    public String getPageHeaderText() {
        return getWait5().until(ExpectedConditions.visibilityOf(header)).getText();
    }
}
