package school.redrover.model.base;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.interfaces.IDescription;
import school.redrover.model.MovePage;
import school.redrover.model.RenamePage;
import school.redrover.model.base.baseConfig.BaseConfigPage;

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

    @Step("Click on the 'Configure' Button on the Base Job Page")
    protected void setupClickConfigure() {
        getWait10().until(ExpectedConditions.elementToBeClickable(configureButton)).click();
    }

    public abstract BaseConfigPage<?,?> clickConfigure();

    @Step("Get the name of the job from Base Job Page")
    public String getJobName() {
        return getWait2().until(ExpectedConditions.visibilityOf(jobName)).getText();
    }

    @Step("Click on the 'Rename' Button on the Base Job Page")
    public RenamePage<Self> clickRename() {
        renameButton.click();

        return new RenamePage<>((Self) this);
    }

    @Step("Click on the 'Move' Button on side menu")
    public MovePage<Self> clickMoveOnSideMenu() {
        getWait5().until(ExpectedConditions.elementToBeClickable(moveButton)).click();

        return new MovePage<>((Self) this);
    }

    @Step("Get the name of the Project with display name from Base Job Page")
    public String getProjectNameSubtitleWithDisplayName() {
        String projectName = mainPanel.getText();
        String subStr = projectName.substring(projectName.indexOf(':') + 2);

        return subStr.substring(0, subStr.indexOf("\n")).trim();
    }

    @Step("Get the name of the Project from Base Job Page")
    public String getProjectName() {
        String projectName = jobName.getText();
        if (projectName.contains("Project") || projectName.contains("Pipeline")) {

            return projectName.substring(projectName.indexOf(" ") + 1);
        } else {

            return projectName;
        }
    }
}
