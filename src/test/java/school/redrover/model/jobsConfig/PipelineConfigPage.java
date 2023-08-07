package school.redrover.model.jobsConfig;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
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

    @FindBy(xpath = "//div[@id='workflow-editor-1']//textarea")
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

    public PipelineConfigPage(PipelinePage pipelinePage) {
        super(pipelinePage);
    }

    @Step("Click on the 'Advanced' arrow in the Advanced Project Options section")
    public PipelineConfigPage scrollAndClickAdvancedButton() {
        TestUtils.scrollWithPauseByActions(this, scriptSection, 500);
        getWait2().until(ExpectedConditions.elementToBeClickable(advancedButton)).click();

        return this;
    }

    @Step("Fill in '{displayName}' Display Name in the Advanced Project Options section")
    public PipelineConfigPage setDisplayName(String displayName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(name)).sendKeys(displayName);

        return this;
    }

    @Step("Scroll to the Pipeline section")
    public PipelineConfigPage scrollToPipelineSection() {
        TestUtils.scrollToElementByJavaScript(this, section);

        return this;
    }

    @Step("Get the name of the selected definition in the Pipeline section")
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

    @Step("Select sample of Pipeline script by value")
    public PipelinePage selectScriptSample(String value) {
        new Select(selectScript).selectByValue(value);

        return new PipelinePage(getDriver());
    }

    @Step("Click on the 'Pipeline' item on the side menu")
    public PipelineConfigPage clickPipelineLeftMenu() {
        getWait5().until(ExpectedConditions.elementToBeClickable(pipeline)).click();

        return this;
    }

    @Step("Input text of the script")
    public PipelineConfigPage inputInScriptField(String scriptText){
        scriptInputField.sendKeys(scriptText);

        return this;
    }
}
