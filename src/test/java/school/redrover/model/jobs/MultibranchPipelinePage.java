package school.redrover.model.jobs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.ScanOtherFoldersPage;
import school.redrover.model.base.BaseOtherFoldersPage;
import school.redrover.model.jobsConfig.MultibranchPipelineConfigPage;

public class MultibranchPipelinePage extends BaseOtherFoldersPage<MultibranchPipelinePage> {

    @FindBy(xpath = "//span[text()='Re-index branches']/..")
    private WebElement reindexBranchesLink;

    public MultibranchPipelinePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public MultibranchPipelineConfigPage clickConfigure() {
        setupClickConfigure();

        return new MultibranchPipelineConfigPage(new MultibranchPipelinePage(getDriver()));
    }

    public ScanOtherFoldersPage clickReindexBranchesLink() {
        getWait10().until(ExpectedConditions.elementToBeClickable(reindexBranchesLink)).click();

        return new ScanOtherFoldersPage(getDriver());
    }
}
