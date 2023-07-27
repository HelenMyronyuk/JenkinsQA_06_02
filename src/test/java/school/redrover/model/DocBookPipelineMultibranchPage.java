package school.redrover.model;

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

    public String getBranchesAndPullRequestsTutorial() {
        return branchesAndPullRequestsTutorial.getText();
    }
}
