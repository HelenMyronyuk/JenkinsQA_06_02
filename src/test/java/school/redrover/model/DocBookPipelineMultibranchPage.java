package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseMainHeaderPage;

public class DocBookPipelineMultibranchPage extends BaseMainHeaderPage<DocBookPipelineMultibranchPage> {

    @FindBy(xpath = "//h1[@id='branches-and-pull-requests']")
    private WebElement branchesAndPullRequestsTutorial;

    public DocBookPipelineMultibranchPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get text 'Branches and Pull Requests' ")
    public String getBranchesAndPullRequestsTutorial() {
        return branchesAndPullRequestsTutorial.getText();
    }
}
