package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;

import java.util.ArrayList;
import java.util.List;

public class GitBuildDataPage extends BaseMainHeaderPage<GitBuildDataPage> {

    @FindBy(xpath = "//div[@id='main-panel']//a[contains(@href, 'github')]")
    private WebElement repositoryNameLink;

    @FindBy(xpath = "//div[@id='main-panel']//ul[2]/li")
    private List<WebElement> builtBranchesList;

    public GitBuildDataPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get repository name")
    public String getRepositoryName() {
        return getWait5().until(ExpectedConditions.visibilityOf(repositoryNameLink)).getText();
    }

    @Step("Get names of built branches")
    public String getNamesOfBuiltBranches() {
        List<String> namesOfBuiltBranches = new ArrayList<>();
        for (WebElement branch : builtBranchesList) {
            String[] nameOfBranch = branch.getText().split(":");
            namesOfBuiltBranches.add(nameOfBranch[0]);
        }
        if (namesOfBuiltBranches.isEmpty()) {

            return "";
        }

        return String.join(", ", namesOfBuiltBranches);
    }
}
