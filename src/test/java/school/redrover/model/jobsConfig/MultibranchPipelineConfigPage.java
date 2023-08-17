package school.redrover.model.jobsConfig;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.WorkflowMultibranchPage;
import school.redrover.model.jobs.MultibranchPipelinePage;
import school.redrover.model.base.baseConfig.BaseConfigFoldersPage;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;

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

    @FindBy(xpath = "//label[text()='Discard old items']")
    private WebElement discardOldItemsLabel;

    @FindBy(xpath = "//label[text()='Periodically if not otherwise run']")
    private WebElement periodicallyOtherwiseCheckBox;

    @FindBy(xpath = "//select[@name='_.interval']")
    private WebElement intervalSelect;

    @FindBy(xpath = "//select[@name='_.interval']/option")
    private List<WebElement> intervalsList;

    @FindBy(xpath = "//button[@data-section-id ='build-configuration']")
    private WebElement buildConfigurationButton;

    @FindBy(xpath = "//a[@tooltip='Help for feature: Script Path']")
    private WebElement scriptPathButton;

    @FindBy(xpath = "//a[normalize-space()='Pipeline: Multibranch']")
    private WebElement pipelineMultibranchPageLink;

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

        for (WebElement option : addSourceOptionsList) {
            optionsList.add(option.getText());
        }

        return optionsList;
    }

    @Step("Click on the 'Periodically if not otherwise run' button from the Configuration page")
    public MultibranchPipelineConfigPage clickPeriodicallyOtherwiseCheckBox() {
        TestUtils.scrollWithPauseByActions(this, discardOldItemsLabel, 600);
        getWait5().until(ExpectedConditions.elementToBeClickable(periodicallyOtherwiseCheckBox)).click();

        return this;
    }

    @Step("Open 'Interval' drop down select from the Configuration page")
    public MultibranchPipelineConfigPage openIntervalDropDownSelect() {
        intervalSelect.click();

        return this;
    }

    @Step("Get an options list from the 'Scan Multibranch Pipeline Triggers. Interval' drop down select")
    public List<String> getIntervalsList() {
        List<String> intervalOptionList = new ArrayList<>();

        for (WebElement interval : intervalsList) {
            intervalOptionList.add(interval.getText());
        }

        return intervalOptionList;
    }

    @Step("Click on the 'Build Configuration' button on the Configuration page to select the option")
    public MultibranchPipelineConfigPage clickBuildConfiguration() {
        getWait5().until(ExpectedConditions.elementToBeClickable(buildConfigurationButton)).click();

        return this;
    }

    @Step("Click on the 'ScriptPath' button on the Build Configuration menu")
    public MultibranchPipelineConfigPage clickScriptPathButton() {

        getWait10().until(ExpectedConditions.elementToBeClickable(scriptPathButton)).click();

        return this;
    }

    @Step("Click on the 'Pipeline: Multibranch' link in the Script Path field to navigate to the 'Pipeline: Multibranch' page")
    public WorkflowMultibranchPage clickPipelineMultibranchPageLink() {

        WebElement element = getWait10().until(ExpectedConditions.elementToBeClickable(pipelineMultibranchPageLink));

        int xCoordinate = element.getLocation().getX();
        int yCoordinate = element.getLocation().getY();

        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        String jsClick = "window.scrollTo(" + xCoordinate + ", " + yCoordinate + ");"
                + "arguments[0].click();";
        jsExecutor.executeScript(jsClick, element);

        return new WorkflowMultibranchPage(getDriver());
    }

}

