package school.redrover.model.base.baseConfig;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseJobPage;

import java.time.Duration;

public abstract class BaseConfigFoldersPage<Self extends BaseConfigPage<?, ?>, FolderPage extends BaseJobPage<?>> extends BaseConfigPage<Self, FolderPage>{

    @FindBy(xpath = "//input[@name='_.displayNameOrNull']")
    private WebElement inputDisplayName;

    @FindBy(xpath = "(//button[contains(text(), 'Health metrics')])[1]")
    private WebElement healthMetric;

    @FindBy(xpath = "//button[@data-section-id='health-metrics']")
    private WebElement healthMetricsSideMenu;

    @FindBy(xpath = "(//button [text()='Add metric'])[1]")
    private WebElement addHealthMetric;

    @FindBy(xpath = "//a[text()='Child item with worst health']")
    private WebElement childItemWithWorstHealth;

    @FindBy(xpath = "//div[@name='healthMetrics']")
    private WebElement addedHealthMetric;

    @FindBy(xpath = "//input[@name='_.recursive']")
    private WebElement recursiveCheckbox;

    @FindBy(xpath = "//button[@tooltip='Remove']")
    private WebElement removeHealthMetric;

    @FindBy(xpath = "//button[@data-section-id ='projects']")
    private WebElement projectsSideMenu;

    @FindBy(xpath = "//input[@name ='_.scriptPath']")
    private WebElement inputScriptPath;

    public BaseConfigFoldersPage(FolderPage foldersPage) {
        super(foldersPage);
    }

    @Step("Enter {displayName} as display name")
    public Self enterDisplayName(String displayName) {
        inputDisplayName.click();
        inputDisplayName.sendKeys(displayName);

        return (Self)this;
    }

   @Step("Clear display name")
    public Self clearDisplayName() {
        inputDisplayName.clear();

        return (Self)this;
    }

    @Step("Clear health metrics")
    public Self clickHealthMetrics() {
        new Actions(getDriver())
                .click(healthMetricsSideMenu)
                .pause(Duration.ofMillis(1000))
                .perform();
        getWait10().until(ExpectedConditions.elementToBeClickable(healthMetric)).click();

        return (Self)this;
    }

    @Step("Add health metrics")
    public Self addHealthMetrics() {
        clickHealthMetrics();

        getWait5().until(ExpectedConditions.elementToBeClickable(addHealthMetric)).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(childItemWithWorstHealth)).click();

        return (Self)this;
    }

    @Step("Check if health metric is visible")
    public Boolean healthMetricIsVisible() {

        return getWait10().until(ExpectedConditions.visibilityOf(addedHealthMetric)).isDisplayed();
    }

    @Step("Delete health metrics")
    public Self removeHealthMetrics() {
        new Actions(getDriver())
                .click(removeHealthMetric)
                .pause(600)
                .perform();

        return (Self) this;
    }

    @Step("Check if health metric is invisible")
    public boolean isHealthMetricInvisible() {
        return getWait2().until(ExpectedConditions.invisibilityOf(addedHealthMetric));
    }

    @Step("Click project's side menu")
    public Self clickProjectsSideMenu() {
        getWait5().until(ExpectedConditions.elementToBeClickable(projectsSideMenu)).click();

        return (Self) this;
    }

    @Step("Enter script path {scriptPath}")
    public Self enterScriptPath(String scriptPath) {
        inputScriptPath.clear();
        inputScriptPath.click();
        inputScriptPath.sendKeys(scriptPath);

        return (Self)this;
    }

    @Step("Get script path")
    public String getScriptPath() {
        return inputScriptPath.getAttribute("value");
    }
}
