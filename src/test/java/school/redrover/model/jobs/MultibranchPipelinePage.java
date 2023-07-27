package school.redrover.model.jobs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.BuildHistoryPage;
import school.redrover.model.PeoplePage;
import school.redrover.model.base.BaseOtherFoldersPage;
import school.redrover.model.jobsconfig.MultibranchPipelineConfigPage;

public class MultibranchPipelinePage extends BaseOtherFoldersPage<MultibranchPipelinePage> {

    public MultibranchPipelinePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public MultibranchPipelineConfigPage clickConfigure() {
        setupClickConfigure();
        return new MultibranchPipelineConfigPage(new MultibranchPipelinePage(getDriver()));
    }
}
