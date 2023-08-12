package school.redrover.model.jobsConfig;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.jobs.MultibranchPipelinePage;
import school.redrover.model.base.baseConfig.BaseConfigFoldersPage;

import java.util.ArrayList;
import java.util.List;

public class MultibranchPipelineConfigPage extends BaseConfigFoldersPage<MultibranchPipelineConfigPage, MultibranchPipelinePage> {

    @FindBy(xpath = "//label[@data-title='Disabled']")
    private WebElement disabledSwitch;

    @FindBy(xpath = "//button[@data-section-id='appearance']")
    private WebElement appearanceButton;

    @FindBy(xpath = "//div[@class='jenkins-form-item has-help']/div/select")
    private WebElement defaultIcon;

    @FindBy(xpath = "(//button [text()='Add metric'])[1]")
    private WebElement addHealthMetric;

    @FindBy(xpath = "//a[text()='Health of the primary branch of a repository']")
    private WebElement healthPrimaryBranchRepositoryOption;

    @FindBy(xpath = "//button[text()='Add source']")
    private WebElement addSourceButton;

    @FindBy(xpath = "//ul[@class='first-of-type']/li")
    private List<WebElement> addSourceOptionsList;

    public MultibranchPipelineConfigPage(MultibranchPipelinePage multibranchPipelinePage) {
        super(multibranchPipelinePage);
    }

    @Step("Click on the 'Disable' button on the Configuration page to disable project")
    public MultibranchPipelineConfigPage clickDisable() {
        getWait5().until(ExpectedConditions.visibilityOf(disabledSwitch)).click();

        return this;
    }

    @Step("Click on the 'Appearance' button on the Configuration page to select the option")
    public MultibranchPipelineConfigPage clickAppearance() {
        getWait5().until(ExpectedConditions.elementToBeClickable(appearanceButton)).click();

        return this;
    }

    @Step("Select an option the 'Default Icon' from the Configuration page")
    public MultibranchPipelineConfigPage selectDefaultIcon() {
        new Select(getWait5().until(ExpectedConditions.elementToBeClickable(defaultIcon)))
                .selectByVisibleText("Default Icon");

        return this;
    }

    @Step("Select an option the 'Health of the primary branch of a repository' from the Health metrics section")
    public MultibranchPipelineConfigPage addHealthMetricPrimaryBranchRepository() {
        getWait5().until(ExpectedConditions.elementToBeClickable(addHealthMetric)).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(healthPrimaryBranchRepositoryOption)).click();

        return this;
    }

    @Step("Click on the 'Add Source' button from the Configuration page")
    public MultibranchPipelineConfigPage clickAddSourceButton() {
        getWait5().until(ExpectedConditions.elementToBeClickable(addSourceButton)).click();

        return this;
    }

    @Step("Get an options list from the 'Add Source' drop down menu")
    public List<String> getAddSourceOptionsList() {
        List<String> optionsList = new ArrayList<>();

        for(WebElement option: addSourceOptionsList) {
            optionsList.add(option.getText());
        }

        return optionsList;
    }
}

