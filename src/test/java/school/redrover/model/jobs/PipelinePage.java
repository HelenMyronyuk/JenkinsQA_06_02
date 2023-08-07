package school.redrover.model.jobs;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.jobsConfig.PipelineConfigPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseProjectPage;

public class PipelinePage extends BaseProjectPage<PipelinePage> {

    @FindBy(css = ".stage-header-name-0")
    private WebElement stage;

    @FindBy(xpath = "//div[@id='pipeline-box']/div")
    private WebElement alertWarning;

    @FindBy(xpath = "//a[@href='lastCompletedBuild/']")
    private WebElement lastCompletedBuildLinkFromPermalinks;

    public PipelinePage(WebDriver driver) {
        super(driver);
    }

    @Step("Click on the Configure button on side menu")
    @Override
    public PipelineConfigPage clickConfigure() {
        setupClickConfigure();

        return new PipelineConfigPage(this);
    }

    @Step("Get stage")
    public String getStage() {
        return stage.getText();
    }

    @Step("Get a text alert")
    public String getAlert() {
        getWait10().until(ExpectedConditions.visibilityOf(lastCompletedBuildLinkFromPermalinks));

        return getWait10().until(ExpectedConditions.visibilityOf(alertWarning)).getText().trim();
    }
}
