package school.redrover.model.base.baseConfig;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BaseProjectPage;
import school.redrover.runner.TestUtils;

import java.util.List;

public abstract class BaseConfigProjectsPage<Self extends BaseConfigPage<?, ?>, ProjectPage extends BaseProjectPage<?>> extends BaseConfigPage<Self, ProjectPage> {

    @FindBy(xpath = "//label[@for='enable-disable-project']")
    private WebElement enableDisableSwitch;

    @FindBy(xpath = "//span[text() = 'Enabled']")
    private WebElement enabledText;

    @FindBy(xpath = "//label[text()='Discard old builds']/../input")
    private WebElement oldBuildCheckBox;

    @FindBy(xpath = "//input[@name='_.daysToKeepStr']")
    private WebElement daysToKeepBuilds;

    @FindBy(xpath = "//div[text()='Days to keep builds']")
    private WebElement nameFieldDaysToKeepBuilds;

    @FindBy(xpath = "//input[@name='_.numToKeepStr']")
    private WebElement maxNumOfBuildsToKeepNumber;

    @FindBy(xpath = "//*[@name='strategy']//div[@class='error']")
    private WebElement errorMessage;

    @FindBy(xpath = "//label[text()='GitHub project']")
    private WebElement checkBoxGitHubProject;

    @FindBy(css = "[name='_.projectUrlStr']")
    private WebElement inputLineProjectUrl;

    @FindBy(xpath = "//label[text()='This project is parameterized']")
    private WebElement projectIsParametrized;

    @FindBy(xpath = "//button[text()='Add Parameter']")
    private WebElement addParameterDropdown;

    @FindBy(xpath = "//button[text()='Add Parameter']/../../..//a")
    private List<WebElement> optionsOfAddParameterDropdown;

    @FindBy(xpath = "//input[@name='parameter.name']")
    private WebElement inputParameterName;

    @FindBy(xpath = "//label[normalize-space(text())='Set by Default']")
    private WebElement checkboxSetByDefault;

    @FindBy(xpath = "//textarea[contains(@name,'parameter.description')]")
    private WebElement textareaBooleanParameterDescription;

    @FindBy(xpath = "//textarea[@name='parameter.choices']")
    private WebElement inputParameterChoices;

    @FindBy(xpath = "//textarea[@name='parameter.description']")
    private WebElement inputParameterDescription;

    @FindBy(xpath = "//label[normalize-space(text())='Throttle builds']")
    private WebElement throttleBuilds;

    @FindBy(xpath = "//select[@name='_.durationName']")
    private WebElement selectTimePeriod;

    @FindBy(xpath = "//input[@name='jenkins-triggers-ReverseBuildTrigger']")
    private WebElement buildAfterOtherProjectsAreBuiltCheckBox;

    @FindBy(xpath = "//input[@name='_.upstreamProjects']")
    private WebElement projectsToWatchField;

    public BaseConfigProjectsPage(ProjectPage projectPage) {
        super(projectPage);
    }

    @Step("Clicl Enable/Disable button")
    public Self clickSwitchEnableOrDisable() {
        getWait2().until(ExpectedConditions.elementToBeClickable(enableDisableSwitch)).click();

        return (Self) this;
    }

    @Step("Get verification if enabled button is switched on")
    public Boolean isEnabledDisplayed() {
        return enabledText.isDisplayed();
    }

    @Step("Click on OldBuild check box")
    public Self clickOldBuildCheckBox() {
        TestUtils.clickByJavaScript(this, oldBuildCheckBox);

        return (Self) this;
    }

    @Step("Get verification if Discard old builds is selected")
    public boolean checkboxDiscardOldBuildsIsSelected() {
        return oldBuildCheckBox.isSelected();
    }

    @Step("Inter {number} days to keep builds")
    public Self enterDaysToKeepBuilds(int number) {
        TestUtils.scrollToElementByJavaScript(this, nameFieldDaysToKeepBuilds);
        TestUtils.sendTextToInput(this, daysToKeepBuilds, String.valueOf(number));

        return (Self) this;
    }

    @Step("Inter maximum number {number} of builds to keep")
    public Self enterMaxNumOfBuildsToKeep(int number) {
        TestUtils.sendTextToInput(this, maxNumOfBuildsToKeepNumber, String.valueOf(number));

        return (Self) this;
    }

    @Step("Get days to keep builds")
    public String getDaysToKeepBuilds() {
        return daysToKeepBuilds.getAttribute("value");
    }

    @Step("Get maximum number of builds to keep")
    public String getMaxNumOfBuildsToKeep() {
        return maxNumOfBuildsToKeepNumber.getAttribute("value");
    }

    @Step("Get maximum number of builds to keep")
    public String getErrorMessageStrategyDays() {
        return getWait2().until(ExpectedConditions.elementToBeClickable(errorMessage)).getText();
    }

    @Step("Click on GitHub Project check box")
    public Self clickGitHubProjectCheckbox() {
        checkBoxGitHubProject.click();

        return (Self) this;
    }

    @Step("Input {text} into input field Project URL GitHub Projects")
    public Self inputTextTheInputAreaProjectUrlInGitHubProject(String text) {
        inputLineProjectUrl.sendKeys(text);

        return (Self) this;
    }

    @Step("Check if project is parametrized")
    public Self checkProjectIsParametrized() {
        getWait5().until(ExpectedConditions.elementToBeClickable(projectIsParametrized)).click();

        return (Self) this;
    }

    @Step("Open AddParameter drop-down menu")
    public Self openAddParameterDropDown() {
        getWait10().until(ExpectedConditions.elementToBeClickable(addParameterDropdown));
        getWait10().until(ExpectedConditions.elementToBeClickable(projectIsParametrized));
        TestUtils.scrollToElementByJavaScript(this, projectIsParametrized);
        addParameterDropdown.click();

        return (Self) this;
    }

    @Step("Select {typeParameter} from drop-down menu")
    public Self selectParameterInDropDownByType(String typeParameter) {
        getDriver().findElement(By.xpath(String.format("//li/a[text()='%s']", typeParameter))).click();

        return (Self) this;
    }

    @Step("Input boolean parameter {name}")
    public Self inputBooleanParameterName(String name) {
        inputParameterName.sendKeys(name);

        return (Self) this;
    }

    @Step("Select check box by default")
    public Self selectCheckboxSetByDefault() {
        getWait5().until(ExpectedConditions.elementToBeClickable(checkboxSetByDefault)).click();

        return (Self) this;
    }

    @Step("Set {description} as boolean parameter description")
    public Self setBooleanParameterDescription(String description) {
        textareaBooleanParameterDescription.sendKeys(description);

        return (Self) this;
    }

    @Step("Input {parameterChoices} as parameter choices")
    public Self inputParameterChoices(List<String> parameterChoices) {
        for (String element : parameterChoices) {
            inputParameterChoices.sendKeys(element + "\n");
        }

        return (Self) this;
    }

    @Step("Input {description} as parameter description")
    public Self inputParameterDesc(String description) {
        inputParameterDescription.sendKeys(description);

        return (Self) this;
    }

    @Step("Get all options of Add parameter drop-down menu")
    public List<String> getAllOptionsOfAddParameterDropdown() {
        return TestUtils.getTexts(optionsOfAddParameterDropdown);
    }

    @Step("Scroll to Build Triggers")
    public Self scrollToBuildTriggers() {
        TestUtils.scrollToElementByJavaScript(this, throttleBuilds);

        return (Self) this;
    }

    @Step("Check Throttle Builds")
    public Self checkThrottleBuilds() {
       throttleBuilds.click();

        return (Self) this;
    }

    @Step("Select {timePeriod} as time period")
    public Self selectTimePeriod(String timePeriod) {
        new Select(selectTimePeriod).selectByValue(timePeriod.toLowerCase());

        return (Self) this;
    }

    @Step("Get time period")
    public String getTimePeriodText() {
        return new Select(selectTimePeriod).getFirstSelectedOption().getText();
    }

    public Self clickBuildAfterOtherProjectsAreBuiltCheckBox() {
        TestUtils.scrollToElementByJavaScript(this, buildAfterOtherProjectsAreBuiltCheckBox);
        TestUtils.clickByJavaScript(this, buildAfterOtherProjectsAreBuiltCheckBox);

        return (Self) this;
    }

    @Step("Input {projectName} to watch")
    public Self inputProjectsToWatch(String projectName) {
        getWait5().until(ExpectedConditions.visibilityOf(projectsToWatchField)).sendKeys(projectName);

        return (Self) this;
    }
}
