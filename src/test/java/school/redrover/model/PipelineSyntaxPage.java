package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;

public class PipelineSyntaxPage extends BaseMainHeaderPage<StatusUserPage>  {

    @FindBy(xpath = "//option[@value='echo: Print Message']")
    private WebElement printMessage;

    @FindBy(name = "_.message")
    private WebElement messageTextField;

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

    public PipelineSyntaxPage clickPrintMessageOption() {
        printMessage.click();

        return this;
    }

    public PipelineSyntaxPage enterMessage(String text) {
        messageTextField.sendKeys(text);

        return this;
    }

    public PipelineSyntaxPage clickGeneratePipelineScriptButton() {
        generatePipelineScriptButton.click();

        return this;
    }

    public String getTextPipelineScript() {
       return textArea.getAttribute("value");
    }

   @Override
    public String getPageHeaderText() {
        return getWait5().until(ExpectedConditions.visibilityOf(header)).getText();
    }

    public String getOverviewText() {
        return getWait5().until(ExpectedConditions.visibilityOf(overviewText)).getText().trim();
    }
}
