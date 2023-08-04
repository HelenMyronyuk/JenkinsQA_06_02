package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;

public class CreateItemErrorPage extends BaseMainHeaderPage<CreateItemErrorPage> {

    @FindBy(xpath = "//div//p")
    private WebElement errorMessage;

    @FindBy(xpath = "//h1")
    private WebElement headerText;

    public CreateItemErrorPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get an Error message text")
    public String getErrorMessage() {
        return getWait10().until(ExpectedConditions.visibilityOf(errorMessage)).getText();
    }

    @Step("Get Heading text from Error page")
    public String getHeaderText() {
        return getWait10().until(ExpectedConditions.visibilityOf(headerText)).getText();
    }
}
