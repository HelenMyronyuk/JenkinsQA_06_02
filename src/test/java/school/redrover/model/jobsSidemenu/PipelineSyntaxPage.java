package school.redrover.model.jobsSidemenu;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BaseMainHeaderPage;

public class PipelineSyntaxPage extends BaseMainHeaderPage<PipelineSyntaxPage> {

    @FindBy(xpath = "//select[@class='jenkins-select__input dropdownList']")
    private WebElement sampleStepDropdown;

    @FindBy(xpath = "//option[@value='echo: Print Message']")
    private WebElement printMessage;

    @FindBy(name = "_.message")
    private WebElement messageTextField;

    @FindBy(name = "_.time")
    private WebElement sleepTimeField;

    @FindBy(name = "_.unit")
    private WebElement unitDropdown;

    @FindBy(xpath = "//button[contains(text(),'Generate')]")
    private WebElement generatePipelineScriptButton;

    @FindBy(xpath = "//textarea")
    private WebElement textArea;

    @FindBy(className = "jenkins-section__title")
    private WebElement header;

    @FindBy(xpath = "//div[contains(text(), 'Overview')]")
    private WebElement overviewText;

    public PipelineSyntaxPage(WebDriver driver) {
        super(driver);
    }

    @Step("Select the 'echo: Print Message' option in 'Sample Step' dropdown menu")
    public PipelineSyntaxPage clickPrintMessageOption() {
        printMessage.click();

        return this;
    }

    @Step("Enter message '{text}' in message field")
    public PipelineSyntaxPage enterMessage(String text) {
        messageTextField.sendKeys(text);

        return this;
    }

    @Step("Enter '{time}' in 'Sleep time' field")
    public PipelineSyntaxPage enterSleepTime(String time) {
        getWait15().until(ExpectedConditions.visibilityOf(sleepTimeField)).sendKeys(time);

        return this;
    }

    @Step("Click 'Generate Pipeline Script' button")
    public PipelineSyntaxPage clickGeneratePipelineScriptButton() {
        generatePipelineScriptButton.click();

        return this;
    }

    @Step("Get Pipeline script text")
    public String getTextPipelineScript() {
        return textArea.getAttribute("value");
    }

    @Step("Get header text")
    @Override
    public String getPageHeaderText() {
        return getWait5().until(ExpectedConditions.visibilityOf(header)).getText();
    }

    @Step("Get Overview text")
    public String getOverviewText() {
        return getWait5().until(ExpectedConditions.visibilityOf(overviewText)).getText().trim();
    }

    @Step("Select '{option}' in 'Sample Step' dropdown menu")
    public PipelineSyntaxPage setSampleStep(String option) {
        Select dropdown = new Select(getWait15().until(ExpectedConditions.visibilityOf(sampleStepDropdown)));
        dropdown.selectByVisibleText(option);

        return this;
    }

    @Step("Select '{option}' in 'Unit' dropdown menu")
    public PipelineSyntaxPage setUnit(String option) {
        Select dropdown = new Select(getWait15().until(ExpectedConditions.visibilityOf(unitDropdown)));
        dropdown.selectByVisibleText(option);

        return this;
    }
}
