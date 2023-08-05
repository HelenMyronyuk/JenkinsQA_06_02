package school.redrover.model.manageJenkins;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.ErrorNodePage;
import school.redrover.model.base.BaseMainHeaderPage;

public class CreateNodePage extends BaseMainHeaderPage<CreateNodePage> {

    @FindBy(xpath = "//input[@name='name']")
    private WebElement inputName;

    @FindBy(xpath = "//textarea[@name='nodeDescription']")
    private WebElement descriptionTextarea;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement saveButton;

    public CreateNodePage(WebDriver driver) {
        super(driver);
    }

    @Step("Input the Name in the field")
    public CreateNodePage clearNameField() {
        getWait2().until(ExpectedConditions.elementToBeClickable(inputName)).clear();

        return this;
    }

    @Step("Click on the 'Save' button and move to Error page")
    public ErrorNodePage clickSaveButtonWhenNameFieldEmpty() {
        getWait2().until(ExpectedConditions.elementToBeClickable(saveButton)).click();

        return new ErrorNodePage(getDriver());
    }

    @Step("Click on the 'Save' button")
    public ManageNodesPage clickSaveButton() {
        getWait10().until(ExpectedConditions.elementToBeClickable(saveButton)).click();

        return new ManageNodesPage(getDriver());
    }

    @Step("Input '{description}' into 'Description' field")
    public CreateNodePage addDescription(String description) {
        getWait2().until(ExpectedConditions
                .elementToBeClickable(descriptionTextarea)).sendKeys(description);

        return this;
    }
}
