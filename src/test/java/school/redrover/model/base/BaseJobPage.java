package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.interfaces.IDescription;
import school.redrover.model.MainPage;
import school.redrover.model.MovePage;
import school.redrover.model.RenamePage;
import school.redrover.model.base.baseConfig.BaseConfigPage;

import java.time.Duration;

public abstract class BaseJobPage<Self extends BaseJobPage<?>> extends BaseMainHeaderPage<Self> implements IDescription<Self> {

    @FindBy(linkText = "Configure")
    private WebElement configureButton;

    @FindBy(xpath = "//h1")
    private WebElement jobName;

    @FindBy(linkText = "Rename")
    private WebElement renameButton;

    @FindBy(partialLinkText = "Delete ")
    private WebElement deleteButton;

    @FindBy(xpath = "//span[text()='Move']/..")
    private WebElement moveButton;

    @FindBy(xpath = "//div[@id='main-panel']")
    private WebElement mainPanel;

    public BaseJobPage(WebDriver driver) {
        super(driver);
    }

    protected void setupClickConfigure() {
        getWait10().until(ExpectedConditions.elementToBeClickable(configureButton)).click();
    }

    public abstract BaseConfigPage<?,?> clickConfigure();

    public String getJobName() {
        return getWait2().until(ExpectedConditions.visibilityOf(jobName)).getText();
    }

    public RenamePage<Self> clickRename() {
        renameButton.click();

        return new RenamePage<>((Self) this);
    }

    public MainPage clickDeleteAndAlert() {
        deleteButton.click();
        getDriver().switchTo().alert().accept();
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(2));

        return new MainPage(getDriver());
    }

    public MovePage<Self> clickMoveOnSideMenu() {
        getWait5().until(ExpectedConditions.elementToBeClickable(moveButton)).click();

        return new MovePage<>((Self) this);
    }

    public String getProjectNameSubtitleWithDisplayName() {
        String projectName = mainPanel.getText();
        String subStr = projectName.substring(projectName.indexOf(':') + 2);

        return subStr.substring(0, subStr.indexOf("\n")).trim();
    }

    public String getProjectName() {
        String projectName = jobName.getText();
        if (projectName.contains("Project") || projectName.contains("Pipeline")) {

            return projectName.substring(projectName.indexOf(" ") + 1);
        } else {

            return projectName;
        }
    }
}
