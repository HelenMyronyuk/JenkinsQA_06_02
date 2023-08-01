package school.redrover.model.jobsConfig;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.jobs.PipelinePage;
import school.redrover.model.base.baseConfig.BaseConfigProjectsPage;
import school.redrover.runner.TestUtils;

import java.util.List;

public class PipelineConfigPage extends BaseConfigProjectsPage<PipelineConfigPage, PipelinePage> {

    @FindBy(xpath = "//div[@class='ace_content']")
    private WebElement scriptSection;

    @FindBy(xpath = "//textarea[@class='ace_text-input']")
    private WebElement scriptInputField;

    @FindBy(xpath = "//div[@class='jenkins-section']//button[@type='button']")
    private WebElement advancedButton;

    @FindBy(xpath = "//input[@name='_.displayNameOrNull']")
    private WebElement name;

    @FindBy(xpath = "//div[@id='pipeline']")
    private WebElement section;

    @FindBy(css = "div[class='jenkins-section'] select.jenkins-select__input.dropdownList>option")
    private List<WebElement> optionText;

    @FindBy(xpath = "//button[@data-section-id='pipeline']")
    private WebElement pipeline;

    @FindBy(xpath = "//div[@class='samples']/select")
    private WebElement selectScript;

    @FindBy(xpath = "//div[@id='workflow-editor-1']//textarea")
    private WebElement workflowEditor;

    public PipelineConfigPage(PipelinePage pipelinePage) {
        super(pipelinePage);
    }

    public PipelineConfigPage scrollAndClickAdvancedButton() {
        TestUtils.scrollWithPauseByActions(this, scriptSection, 500);
        getWait2().until(ExpectedConditions.elementToBeClickable(advancedButton)).click();

        return this;
    }

    public PipelineConfigPage setDisplayName(String displayName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(name)).sendKeys(displayName);

        return this;
    }

    public PipelineConfigPage scrollToPipelineSection() {
        TestUtils.scrollToElementByJavaScript(this, section);

        return this;
    }

    public String getOptionTextInDefinitionField() {
        String text = "";

        for (WebElement element : optionText) {
            if (element.getAttribute("selected") != null &&
                    element.getAttribute("selected").equals("true")) {
                text = element.getText();
            }
        }

        return text;
    }

    public PipelinePage selectHelloWord() {
        new Select(selectScript).selectByValue("hello");

        return new PipelinePage(getDriver());
    }

    public PipelinePage selectScriptedPipeline() {
        new Select(selectScript).selectByValue("scripted");

        return new PipelinePage(getDriver());
    }

    public PipelineConfigPage clickPipelineLeftMenu() {
        getWait5().until(ExpectedConditions.elementToBeClickable(pipeline)).click();

        return this;
    }

    public PipelineConfigPage sendAreContentInputString(String text) {
        TestUtils.clickByJavaScript(this, workflowEditor);
        workflowEditor.sendKeys(text);

        return this;
    }

    public PipelineConfigPage inputInScriptField(String scriptText){
        scriptInputField.sendKeys(scriptText);

        return this;
    }
}
