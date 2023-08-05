package school.redrover.model.jobs;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.jobsSidemenu.ScanOtherFoldersPage;
import school.redrover.model.base.BaseOtherFoldersPage;
import school.redrover.model.jobsConfig.MultibranchPipelineConfigPage;

public class MultibranchPipelinePage extends BaseOtherFoldersPage<MultibranchPipelinePage> {

    @FindBy(xpath = "//span[text()='Re-index branches']/..")
    private WebElement reindexBranchesLink;

    public MultibranchPipelinePage(WebDriver driver) {
        super(driver);
    }

    @Step("Click on the 'Configure' button on side menu")
    @Override
    public MultibranchPipelineConfigPage clickConfigure() {
        setupClickConfigure();

        return new MultibranchPipelineConfigPage(new MultibranchPipelinePage(getDriver()));
    }

    @Step("Click on the 'Configure' button on side menu")
    @Override
    public MultibranchPipelineConfigPage clickConfigureProject() {
        setupClickConfigureProject();

        return new MultibranchPipelineConfigPage(this);
    }

    @Step("Click on the 'Re-index branches' link on the Multibranch Pipeline page")
    public ScanOtherFoldersPage clickReindexBranchesLink() {
        getWait10().until(ExpectedConditions.elementToBeClickable(reindexBranchesLink)).click();

        return new ScanOtherFoldersPage(getDriver());
    }
}
