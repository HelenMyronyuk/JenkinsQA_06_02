package school.redrover.model.manageJenkins;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.ErrorNodePage;
import school.redrover.model.base.BaseMainHeaderPage;

public class NewNodePage extends BaseMainHeaderPage<NewNodePage> {

    @FindBy(xpath = "//input[@id='name']")
    private WebElement nodeNameField;

    @FindBy(xpath = "//label")
    private WebElement permanentAgentButton;

    @FindBy(xpath = "//label[@for='copy']")
    private WebElement copyExistingNodeButton;

    @FindBy(xpath = "//input[@name='from']")
    private WebElement existingNodeField;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement createButton;

    public NewNodePage(WebDriver driver){
        super(driver);
    }

    @Step("Input Node name '{text}' in field")
    public NewNodePage inputNodeNameField(String text) {
        getWait2().until(ExpectedConditions
                .elementToBeClickable(nodeNameField)).sendKeys(text);

        return this;
    }
    @Step("Click on the 'Permanent Agent' Radio button on the New node page")
    public NewNodePage clickPermanentAgentRadioButton() {
        getWait2().until(ExpectedConditions.elementToBeClickable(permanentAgentButton)).click();

        return this;
    }

    @Step("Click on the 'Copy Existing Node' Radio button on the New node page")
    public NewNodePage clickCopyExistingNode(){
        getWait2().until(ExpectedConditions.elementToBeClickable(copyExistingNodeButton)).click();

        return this;
    }

    @Step("Input existing Node '{existingNodeName}' ")
    public NewNodePage inputExistingNode(String existingNodeName){
        getWait5().until(ExpectedConditions
                .visibilityOf(existingNodeField)).sendKeys(existingNodeName);

        return this;
    }

    @Step("Click on the 'Create' button on the New node page")
    public CreateNodePage clickCreateButton() {
        getWait2().until(ExpectedConditions
                .elementToBeClickable(createButton)).click();

        return new CreateNodePage(getDriver());
    }

    @Step("Click on the 'Create' button on the New node page and go error")
    public ErrorNodePage clickCreateButtonAndGoError() {
        getWait2().until(ExpectedConditions
                .elementToBeClickable(createButton)).click();

        return new ErrorNodePage(getDriver());
    }
}
