package school.redrover.model.builds;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.GitBuildDataPage;
import school.redrover.model.base.BaseSubmenuPage;

public class ConsoleOutputPage extends BaseSubmenuPage<ConsoleOutputPage> {

    @FindBy(xpath = "//pre[@class='console-output']")
    private WebElement consoleOutput;

    @FindBy(xpath = "//a[contains(@class,'model-link--float')]")
    private WebElement startedByUser;

    @FindBy(xpath = "(//*[name()='svg'][@title='Success'])[1]")
    private WebElement greenIconV;

    @FindBy(css = ".jenkins-icon-adjacent")
    private WebElement buildTitle;

    @FindBy(xpath = "//span[text()='Git Build Data']/..")
    private WebElement gitBuildDataLink;

    public ConsoleOutputPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "console";
    }

    @Step("get text from console output")
    public String getConsoleOutputText() {
        getWait5().until(ExpectedConditions.textToBePresentInElement(consoleOutput, "Finished"));

        return consoleOutput.getText();
    }

    @Step("get parameter from console output")
    public String getParameterFromConsoleOutput(String consoleText, String containParameterText) {
        String[] split = consoleText.split("\n");
        for (String str : split) {
            if (str.contains(containParameterText)) {

                return str;
            }
        }

        return null;
    }

    public String getStartedByUser() {
        return getWait2().until(ExpectedConditions.visibilityOf(startedByUser)).getText();
    }

    @Step("Check if Green icon is displayed")
    public boolean isDisplayedGreenIconV() {
        return getWait2().until(ExpectedConditions.visibilityOf(greenIconV)).isDisplayed();
    }

    @Step("Check if Build title  is displayed")
    public boolean isDisplayedBuildTitle() {
        return getWait2().until(ExpectedConditions.visibilityOf(buildTitle)).isDisplayed();
    }

    public BuildPage clickNumberBuild(int buildNumber) {
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text() ,'#" + buildNumber +  "')]"))).click();

        return new BuildPage(getDriver());
    }

    @Step("click git Build Link ConsoleOutput Page")
    public GitBuildDataPage clickGitBuildDataLink() {
        getWait15().until(ExpectedConditions.elementToBeClickable(gitBuildDataLink)).click();

        return new GitBuildDataPage(getDriver());
    }
}
